package com.patflow.app.domain.usecase.insights

import com.patflow.app.domain.model.*
import com.patflow.app.domain.repository.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*
import javax.inject.Inject

/**
 * On-device insight engine (Architecture §Phase 11).
 * Analyzes financial patterns and generates contextual recommendations.
 */
class GetSmartInsightsUseCase @Inject constructor(
    private val billRepository: BillRepository,
    private val paymentRepository: PaymentRepository,
    private val incomeRepository: IncomeRepository,
    private val budgetRepository: BudgetRepository,
    private val savingsRepository: SavingsGoalRepository
) {
    operator fun invoke(): Flow<List<FinancialInsight>> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val startOfMonth = LocalDate(now.year, now.month, 1)

        return combine(
            billRepository.getBillsWithCycles(),
            paymentRepository.getPayments(),
            incomeRepository.getEntries(),
            budgetRepository.getBudgets(),
            savingsRepository.getGoals()
        ) { args: Array<Any?> ->
            @Suppress("UNCHECKED_CAST")
            val billDetails = args[0] as List<BillWithCycle>
            @Suppress("UNCHECKED_CAST")
            val payments = args[1] as List<PaymentHistory>
            @Suppress("UNCHECKED_CAST")
            val income = args[2] as List<IncomeWithDetails>
            @Suppress("UNCHECKED_CAST")
            val budgets = args[3] as List<Budget>
            @Suppress("UNCHECKED_CAST")
            val goals = args[4] as List<SavingsGoal>

            val insights = mutableListOf<FinancialInsight>()

            // 1. Budget Alerts
            val activeBudget = budgets.firstOrNull { it.isActive && !it.isArchived }
            if (activeBudget != null) {
                val spent = payments.filter { it.payment.paymentDate >= activeBudget.startDate }.sumOf { it.payment.amount }
                if (spent > activeBudget.totalAmount) {
                    insights.add(FinancialInsight("Budget Exceeded", "You've spent over your ${activeBudget.name}.", InsightPriority.HIGH))
                }
            }

            // 2. Bill Due Alerts
            val upcomingBills = billDetails.filter { 
                val cycle = it.currentCycle
                cycle != null && cycle.status != BillStatus.PAID && cycle.dueDate <= now.plus(DatePeriod(days = 3)) 
            }
            if (upcomingBills.isNotEmpty()) {
                insights.add(FinancialInsight("Upcoming Bills", "You have ${upcomingBills.size} bills due in the next 3 days.", InsightPriority.MEDIUM))
            }

            // 3. Savings Progress
            val nearGoal = goals.filter { !it.isCompleted && !it.isArchived && it.targetAmount > 0 }.maxByOrNull { it.currentAmount / it.targetAmount }
            nearGoal?.let {
                val progress = (it.currentAmount / it.targetAmount * 100).toInt()
                if (progress >= 80) {
                    insights.add(FinancialInsight("Goal Near Completion", "Your goal '${it.name}' is $progress% complete!", InsightPriority.LOW))
                }
            }

            // 4. Cash Flow Insight
            val monthIncome = income.filter { it.entry.entryDate >= startOfMonth }.sumOf { it.entry.amount }
            val monthSpent = payments.filter { it.payment.paymentDate >= startOfMonth }.sumOf { it.payment.amount }
            if (monthSpent > monthIncome && monthIncome > 0) {
                insights.add(FinancialInsight("Negative Cash Flow", "Your spending this month exceeds your income.", InsightPriority.MEDIUM))
            }

            insights.sortedBy { it.priority }.reversed()
        }
    }
}

data class FinancialInsight(
    val title: String,
    val message: String,
    val priority: InsightPriority
)

enum class InsightPriority { LOW, MEDIUM, HIGH }
