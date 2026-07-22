package com.patflow.app.domain.usecase.report

import com.patflow.app.domain.model.*
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.*
import java.util.Locale
import javax.inject.Inject

class GetReportDataUseCase @Inject constructor(
    private val billRepository: BillRepository,
    private val paymentRepository: PaymentRepository
) {
    operator fun invoke(filter: ReportFilter): Flow<ReportData> {
        val (start, end) = getRange(filter)
        
        return combine(
            billRepository.getBillsWithCycles(),
            billRepository.getCyclesByDateRange(start.toString(), end.toString()),
            paymentRepository.getPayments()
        ) { allBills, cycles, allPayments ->
            
            // 1. Filtering data to the requested range
            val filteredPayments = allPayments.filter { it.payment.paymentDate in start..end }
            
            // 2. Financial Summary
            val totalExpenses = cycles.sumOf { it.amountDue }
            val totalPaid = cycles.sumOf { it.amountPaid }
            val balance = (totalExpenses - totalPaid).coerceAtLeast(0.0)
            
            val summary = FinancialSummary(
                totalExpenses = totalExpenses,
                totalPaid = totalPaid,
                outstandingBalance = balance,
                totalBills = cycles.size,
                totalPayments = filteredPayments.size
            )

            // 3. Category Analysis
            val categorySpending = cycles.groupBy { cycle -> 
                allBills.find { it.bill.id == cycle.billId }?.bill?.category
            }.mapNotNull { (category, cycles) ->
                category?.let { it to cycles.sumOf { c -> c.amountPaid } }
            }.toMap()

            val highestCategory = categorySpending.maxByOrNull { it.value }?.key
            val lowestCategory = categorySpending.minByOrNull { it.value }?.key

            val categoryAnalysis = CategoryAnalysis(
                spendingByCategory = categorySpending,
                highestSpendingCategory = highestCategory,
                lowestSpendingCategory = lowestCategory
            )

            // 4. Trend Analysis
            val trendSpending = filteredPayments.groupBy { 
                val d = it.payment.paymentDate
                "${d.month.name.take(3)} ${d.year}"
            }.mapValues { it.value.sumOf { p -> p.payment.amount } }

            val trendAnalysis = TrendAnalysis(
                monthlySpending = trendSpending,
                billsCreatedPerMonth = emptyMap(), // Simplification for MVP
                paymentsCompletedPerMonth = emptyMap()
            )

            // 5. Payment Performance
            val paidOnTime = cycles.count { it.status == BillStatus.PAID } // Simplified: assume all PAID are on-time for MVP
            val onTimePct = if (cycles.isNotEmpty()) (paidOnTime.toFloat() / cycles.size) * 100 else 0f
            
            val performance = PaymentPerformance(
                onTimePercentage = onTimePct,
                latePercentage = 100 - onTimePct,
                averagePaymentDelayDays = 0, // Placeholder
                overdueBillsCount = cycles.count { it.status == BillStatus.OVERDUE }
            )

            // 6. Insights
            val insights = mutableListOf<String>()
            insights.add("You spent ₱${String.format(Locale.getDefault(), "%.2f", totalPaid)} in this period.")
            if (highestCategory != null) {
                val pct = if (totalPaid > 0) (categorySpending[highestCategory]!! / totalPaid) * 100 else 0.0
                insights.add("${highestCategory.name} accounts for ${String.format(Locale.getDefault(), "%.0f", pct)}% of your spending.")
            }
            insights.add("You paid ${String.format(Locale.getDefault(), "%.0f", onTimePct)}% of your bills on time.")

            ReportData(
                summary = summary,
                categoryAnalysis = categoryAnalysis,
                trendAnalysis = trendAnalysis,
                performance = performance,
                insights = insights
            )
        }
    }

    private fun getRange(filter: ReportFilter): Pair<LocalDate, LocalDate> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        return when (filter) {
            ReportFilter.ThisMonth -> {
                val start = LocalDate(now.year, now.month, 1)
                val end = start.plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
                start to end
            }
            ReportFilter.Last3Months -> {
                val end = now
                val start = LocalDate(now.year, now.month, 1).minus(DatePeriod(months = 2))
                start to end
            }
            ReportFilter.Last6Months -> {
                val end = now
                val start = LocalDate(now.year, now.month, 1).minus(DatePeriod(months = 5))
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
