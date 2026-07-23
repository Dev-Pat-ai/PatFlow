package com.patflow.app.domain.model

/**
 * Composite model for the Dashboard screen (Architecture §7).
 */
data class DashboardData(
    val totalBillsThisMonth: Double = 0.0,
    val totalPaidThisMonth: Double = 0.0,
    val totalRemaining: Double = 0.0,
    val totalIncomeThisMonth: Double = 0.0,
    val totalIncomeThisYear: Double = 0.0,
    val netCashFlow: Double = 0.0,
    val netBalance: Double = 0.0,
    val billsDueToday: Int = 0,
    val upcomingBillsCount: Int = 0,
    val overdueBillsCount: Int = 0,
    val upcomingBills: List<BillWithCycle> = emptyList(),
    val recentPayments: List<PaymentHistory> = emptyList(),
    val spendingTrend: Map<String, Double> = emptyMap(), // Month name -> Amount
    val spendingByCategory: Map<Category, Double> = emptyMap(),
    val incomeByCategory: Map<IncomeCategory, Double> = emptyMap(),
    val budgetAnalytics: BudgetAnalytics? = null,
    val savingsGoals: List<SavingsGoalAnalytics> = emptyList(),
    val insights: List<String> = emptyList()
)
