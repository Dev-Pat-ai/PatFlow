package com.patflow.app.domain.model

import kotlinx.datetime.LocalDate

/**
 * High-level report data containing all analytics for a specific range.
 */
data class ReportData(
    val summary: FinancialSummary,
    val categoryAnalysis: CategoryAnalysis,
    val trendAnalysis: TrendAnalysis,
    val performance: PaymentPerformance,
    val insights: List<String>
)

data class FinancialSummary(
    val totalExpenses: Double,
    val totalPaid: Double,
    val outstandingBalance: Double,
    val totalBills: Int,
    val totalPayments: Int
)

data class CategoryAnalysis(
    val spendingByCategory: Map<Category, Double>,
    val highestSpendingCategory: Category?,
    val lowestSpendingCategory: Category?
)

data class TrendAnalysis(
    val monthlySpending: Map<String, Double>, // Month name -> Amount
    val billsCreatedPerMonth: Map<String, Int>,
    val paymentsCompletedPerMonth: Map<String, Int>
)

data class PaymentPerformance(
    val onTimePercentage: Float,
    val latePercentage: Float,
    val averagePaymentDelayDays: Int,
    val overdueBillsCount: Int
)

/**
 * Filter types for report generation.
 */
sealed interface ReportFilter {
    data object ThisMonth : ReportFilter
    data object Last3Months : ReportFilter
    data object Last6Months : ReportFilter
    data object ThisYear : ReportFilter
    data class Custom(val start: LocalDate, val end: LocalDate) : ReportFilter
}
