package com.patflow.app.domain.usecase.budget

import com.patflow.app.domain.model.BudgetAnalytics
import com.patflow.app.domain.model.BudgetLimitWithUsage
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.BudgetRepository
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Aggregates data to compute budget utilization and spending forecasts (Architecture §Phase 10).
 */
class GetBudgetAnalyticsUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val paymentRepository: PaymentRepository,
    private val billRepository: BillRepository
) {
    operator fun invoke(budgetId: Long): Flow<BudgetAnalytics?> {
        return combine(
            flow { emit(budgetRepository.getBudgetById(budgetId)) },
            budgetRepository.getCategoryLimits(budgetId),
            paymentRepository.getPayments(),
            billRepository.getBillsWithCycles()
        ) { budget, limits, payments, _ ->
            if (budget == null) return@combine null

            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            
            // Filter payments within budget range
            val filteredPayments = payments.filter { 
                it.payment.paymentDate in budget.startDate..budget.endDate 
            }
            
            val amountUsed = filteredPayments.sumOf { it.payment.amount }
            val remaining = (budget.totalAmount - amountUsed).coerceAtLeast(0.0)
            val percentage = if (budget.totalAmount > 0) (amountUsed / budget.totalAmount).toFloat() else 0f
            
            val totalDays = budget.startDate.daysUntil(budget.endDate) + 1
            val daysPassed = budget.startDate.daysUntil(now).coerceIn(0, totalDays)
            val daysRemaining = (totalDays - daysPassed).coerceAtLeast(0)
            
            val avgDaily = if (daysPassed > 0) amountUsed / daysPassed else amountUsed
            val dailyAllowance = if (daysRemaining > 0) remaining / daysRemaining else remaining
            val forecast = avgDaily * totalDays

            val limitUsage = limits.map { limit ->
                val categoryUsed = filteredPayments
                    .filter { it.category.id == limit.category.id }
                    .sumOf { it.payment.amount }
                BudgetLimitWithUsage(
                    limit = limit,
                    amountUsed = categoryUsed,
                    percentageUsed = if (limit.limitAmount > 0) (categoryUsed / limit.limitAmount).toFloat() else 0f
                )
            }

            BudgetAnalytics(
                budget = budget,
                amountUsed = amountUsed,
                remainingAmount = remaining,
                percentageUsed = percentage,
                dailyAllowance = dailyAllowance,
                averageDailySpending = avgDaily,
                forecastEndAmount = forecast,
                isOverspent = amountUsed > budget.totalAmount,
                remainingDays = daysRemaining,
                categoryLimits = limitUsage
            )
        }
    }
}
