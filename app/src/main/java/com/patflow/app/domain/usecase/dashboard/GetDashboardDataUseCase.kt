package com.patflow.app.domain.usecase.dashboard

import com.patflow.app.domain.model.*
import com.patflow.app.domain.repository.*
import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase
import com.patflow.app.domain.usecase.savings.GetSavingsGoalAnalyticsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*
import java.util.Locale
import javax.inject.Inject

/**
 * Use case for aggregating all data required for the Dashboard screen (Architecture §1.3).
 * Updated in Phase 13 with stability improvements and enhanced error handling.
 */
class GetDashboardDataUseCase @Inject constructor(
    private val billRepository: BillRepository,
    private val paymentRepository: PaymentRepository,
    private val incomeRepository: IncomeRepository,
    private val budgetRepository: BudgetRepository,
    private val savingsRepository: SavingsGoalRepository,
    private val getBudgetAnalyticsUseCase: GetBudgetAnalyticsUseCase,
    private val getGoalAnalyticsUseCase: GetSavingsGoalAnalyticsUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<DashboardData> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val startOfMonth = LocalDate(now.year, now.month, 1)
        val endOfMonth = (startOfMonth + DatePeriod(months = 1)) - DatePeriod(days = 1)
        val startOfYear = LocalDate(now.year, 1, 1)
        val sixMonthsAgo = startOfMonth - DatePeriod(months = 5)

        return combine(
            billRepository.getBillsWithCycles().catch { emit(emptyList()) },
            billRepository.getCyclesByDateRange(startOfMonth.toString(), endOfMonth.toString()).catch { emit(emptyList()) },
            paymentRepository.getPayments().catch { emit(emptyList()) },
            incomeRepository.getEntries().catch { emit(emptyList()) },
            budgetRepository.getBudgets().catch { emit(emptyList()) },
            savingsRepository.getGoals().catch { emit(emptyList()) }
        ) { args: Array<Any?> ->
            @Suppress("UNCHECKED_CAST")
            val allBillDetails = args[0] as List<BillWithCycle>
            @Suppress("UNCHECKED_CAST")
            val monthCycles = args[1] as List<BillCycle>
            @Suppress("UNCHECKED_CAST")
            val allPayments = args[2] as List<PaymentHistory>
            @Suppress("UNCHECKED_CAST")
            val allIncome = args[3] as List<IncomeWithDetails>
            @Suppress("UNCHECKED_CAST")
            val budgets = args[4] as List<Budget>
            @Suppress("UNCHECKED_CAST")
            val goals = args[5] as List<SavingsGoal>

            val allBills = allBillDetails.map { it.bill }
            val activeBudget = budgets.firstOrNull { it.isActive && !it.isArchived }
            val activeGoals = goals.filter { !it.isDeleted && !it.isArchived && !it.isCompleted }

            activeBudget to (activeGoals to (allBills to (monthCycles to (allPayments to allIncome))))
        }.flatMapLatest { (budget, goalData) ->
            val (activeGoals, billData) = goalData
            val (allBills, moreData) = billData
            val (monthCycles, evenMoreData) = moreData
            val (allPayments, allIncome) = evenMoreData
            
            val budgetFlow = if (budget != null) getBudgetAnalyticsUseCase(budget.id) else flowOf(null)
            val goalsFlow = if (activeGoals.isNotEmpty()) {
                combine(activeGoals.map { getGoalAnalyticsUseCase(it.id) }) { it.filterNotNull() }
            } else flowOf(emptyList())

            combine(budgetFlow, goalsFlow) { analytics, goalAnalyticsList ->
                val totalPaid = monthCycles.sumOf { it.amountPaid }
                val monthIncome = allIncome.filter { it.entry.entryDate in startOfMonth..endOfMonth }
                val totalIncomeMonth = monthIncome.sumOf { it.entry.amount }
                
                val netCashFlow = totalIncomeMonth - totalPaid
                val totalAllIncome = allIncome.sumOf { it.entry.amount }
                val totalAllPaid = allPayments.sumOf { it.payment.amount }
                
                val insights = mutableListOf<String>()
                if (analytics?.isOverspent == true) insights.add("Budget exceeded! You are overspent.")
                
                if (netCashFlow > 0) insights.add("Good job! You've saved ${String.format(Locale.getDefault(), "₱%.2f", netCashFlow)} this month.")
                if (goalAnalyticsList.any { it.progressPercentage >= 0.9f }) insights.add("You're almost there! A savings goal is over 90% complete.")

                DashboardData(
                    totalBillsThisMonth = monthCycles.sumOf { it.amountDue },
                    totalPaidThisMonth = totalPaid,
                    totalRemaining = (monthCycles.sumOf { it.amountDue } - totalPaid).coerceAtLeast(0.0),
                    totalIncomeThisMonth = totalIncomeMonth,
                    totalIncomeThisYear = allIncome.filter { it.entry.entryDate >= startOfYear }.sumOf { it.entry.amount },
                    netCashFlow = netCashFlow,
                    netBalance = totalAllIncome - totalAllPaid,
                    billsDueToday = monthCycles.count { (it.dueDate == now) && (it.status != BillStatus.PAID) },
                    upcomingBillsCount = monthCycles.count { it.status != BillStatus.PAID },
                    overdueBillsCount = monthCycles.count { it.status == BillStatus.OVERDUE },
                    upcomingBills = monthCycles.asSequence()
                        .filter { it.status != BillStatus.PAID }
                        .sortedBy { it.dueDate }.take(5)
                        .mapNotNull { c -> allBills.find { it.id == c.billId }?.let { BillWithCycle(it, c) } }
                        .toList(),
                    recentPayments = allPayments.take(5),
                    spendingTrend = allPayments.filter { it.payment.paymentDate >= sixMonthsAgo }
                        .groupBy { d -> "${d.payment.paymentDate.month.name.take(3)} ${d.payment.paymentDate.year}" }
                        .mapValues { it.value.sumOf { p -> p.payment.amount } }.toSortedMap(),
                    spendingByCategory = monthCycles.groupBy { c -> allBills.find { it.id == c.billId }?.category }
                        .mapNotNull { (cat, cs) -> cat?.let { it to cs.sumOf { c -> c.amountPaid } } }.toMap(),
                    incomeByCategory = monthIncome.groupBy { it.entry.category }
                        .mapValues { it.value.sumOf { e -> e.entry.amount } },
                    budgetAnalytics = analytics,
                    savingsGoals = goalAnalyticsList,
                    insights = insights
                )
            }
        }.catch { emit(DashboardData()) } // Fallback to empty data on severe failure
    }
}
