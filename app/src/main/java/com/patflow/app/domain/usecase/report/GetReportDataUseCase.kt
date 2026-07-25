package com.patflow.app.domain.usecase.report

import com.patflow.app.domain.model.*
import com.patflow.app.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.*
import java.util.Locale
import javax.inject.Inject

/**
 * Use case for generating comprehensive financial analytics for a specific range (Architecture §1.6).
 * Computes summaries, category distributions, and trends for Income, Expenses, Budgets, and Savings.
 */
class GetReportDataUseCase @Inject constructor(
    private val billRepository: BillRepository,
    private val paymentRepository: PaymentRepository,
    private val incomeRepository: IncomeRepository,
    private val budgetRepository: BudgetRepository,
    private val savingsRepository: SavingsGoalRepository
) {
    operator fun invoke(filter: ReportFilter): Flow<ReportData> {
        val (start, end) = getRange(filter)
        
        return combine(
            billRepository.getBillsWithCycles(),
            billRepository.getCyclesByDateRange(start.toString(), end.toString()),
            paymentRepository.getPayments(),
            incomeRepository.getEntries(),
            budgetRepository.getBudgets(),
            savingsRepository.getGoals()
        ) { args: Array<Any?> ->
            @Suppress("UNCHECKED_CAST")
            val allBills = args[0] as List<BillWithCycle>
            @Suppress("UNCHECKED_CAST")
            val cycles = args[1] as List<BillCycle>
            @Suppress("UNCHECKED_CAST")
            val allPayments = args[2] as List<PaymentHistory>
            @Suppress("UNCHECKED_CAST")
            val allIncome = args[3] as List<IncomeWithDetails>
            @Suppress("UNCHECKED_CAST")
            val budgets = args[4] as List<Budget>
            @Suppress("UNCHECKED_CAST")
            val goals = args[5] as List<SavingsGoal>
            
            // 1. Filtering data to the requested range
            val filteredPayments = allPayments.asSequence()
                .filter { it.payment.paymentDate in start..end }
                .toList()
            val filteredIncome = allIncome.asSequence()
                .filter { it.entry.entryDate in start..end }
                .toList()
            val filteredBudgets = budgets.filter { it.startDate >= start && it.endDate <= end }
            
            // 2. Financial Summary
            val totalExpenses = cycles.sumOf { it.amountDue }
            val totalPaid = cycles.sumOf { it.amountPaid }
            val totalIncome = filteredIncome.sumOf { it.entry.amount }
            val totalBudget = filteredBudgets.sumOf { it.totalAmount }
            val totalSaved = goals.filter { it.createdAt.date in start..end }.sumOf { it.currentAmount } // Simplified
            val balance = (totalExpenses - totalPaid).coerceAtLeast(0.0)
            val netCashFlow = totalIncome - totalPaid
            
            val summary = FinancialSummary(
                totalExpenses = totalExpenses,
                totalPaid = totalPaid,
                totalIncome = totalIncome,
                totalBudget = totalBudget,
                totalSaved = totalSaved,
                outstandingBalance = balance,
                netCashFlow = netCashFlow,
                totalBills = cycles.size,
                totalPayments = filteredPayments.size,
                totalIncomeEntries = filteredIncome.size
            )

            // 3. Category Analysis
            val categorySpending = cycles.groupBy { cycle -> 
                allBills.find { it.bill.id == cycle.billId }?.bill?.category
            }.mapNotNull { (category, cycles) ->
                category?.let { it to cycles.sumOf { c -> c.amountPaid } }
            }.toMap()

            val categoryIncome = filteredIncome.groupBy { it.entry.category }
                .mapValues { it.value.sumOf { e -> e.entry.amount } }

            val highestSpending = categorySpending.maxByOrNull { it.value }?.key
            val lowestSpending = categorySpending.minByOrNull { it.value }?.key
            val highestIncome = categoryIncome.maxByOrNull { it.value }?.key

            val categoryAnalysis = CategoryAnalysis(
                spendingByCategory = categorySpending,
                incomeByCategory = categoryIncome,
                budgetByCategory = emptyMap(),
                highestSpendingCategory = highestSpending,
                lowestSpendingCategory = lowestSpending,
                highestIncomeCategory = highestIncome
            )

            // 4. Trend Analysis
            val trendSpending = filteredPayments.groupBy { 
                val d = it.payment.paymentDate
                "${d.month.name.take(3)} ${d.year}"
            }.mapValues { it.value.sumOf { p -> p.payment.amount } }

            val trendIncome = filteredIncome.groupBy {
                val d = it.entry.entryDate
                "${d.month.name.take(3)} ${d.year}"
            }.mapValues { it.value.sumOf { e -> e.entry.amount } }

            val trendAnalysis = TrendAnalysis(
                monthlySpending = trendSpending,
                monthlyIncome = trendIncome,
                billsCreatedPerMonth = emptyMap(),
                paymentsCompletedPerMonth = emptyMap()
            )

            // 5. Payment Performance
            val paidOnTime = cycles.count { it.status == BillStatus.PAID }
            val onTimePct = if (cycles.isNotEmpty()) (paidOnTime.toFloat() / cycles.size) * 100 else 0f
            
            val performance = PaymentPerformance(
                onTimePercentage = onTimePct,
                latePercentage = 100 - onTimePct,
                averagePaymentDelayDays = 0,
                overdueBillsCount = cycles.count { it.status == BillStatus.OVERDUE }
            )

            // 6. Insights
            val insights = mutableListOf<String>()
            insights.add("You spent ₱${String.format(Locale.getDefault(), "%.2f", totalPaid)} in this period.")
            insights.add("You received ₱${String.format(Locale.getDefault(), "%.2f", totalIncome)} in this period.")
            
            if (totalBudget > 0) {
                val utilization = (totalPaid / totalBudget) * 100
                insights.add("You've used ${String.format(Locale.getDefault(), "%.1f", utilization)}% of your planned budget.")
            }

            ReportData(
                summary = summary,
                categoryAnalysis = categoryAnalysis,
                trendAnalysis = trendAnalysis,
                performance = performance,
                insights = insights,
                recentTransactions = filteredPayments
            )
        }
    }

    private fun getRange(filter: ReportFilter): Pair<LocalDate, LocalDate> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        return when (filter) {
            ReportFilter.ThisMonth -> {
                val start = LocalDate(now.year, now.month, 1)
                val end = (start + DatePeriod(months = 1)) - DatePeriod(days = 1)
                start to end
            }
            ReportFilter.Last3Months -> {
                val end = now
                val start = LocalDate(now.year, now.month, 1) - DatePeriod(months = 2)
                start to end
            }
            ReportFilter.Last6Months -> {
                val end = now
                val start = LocalDate(now.year, now.month, 1) - DatePeriod(months = 5)
                start to end
            }
            ReportFilter.ThisYear -> {
                val start = LocalDate(now.year, 1, 1)
                val end = LocalDate(now.year, 12, 31)
                start to end
            }
            is ReportFilter.Custom -> filter.start to filter.end
        }
    }
}
