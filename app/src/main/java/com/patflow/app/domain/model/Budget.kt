package com.patflow.app.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * Domain model representing a budget (Architecture §1.5 / Phase 10).
 */
data class Budget(
    val id: Long = 0,
    val name: String,
    val type: BudgetType,
    val totalAmount: Double,
    val currencyCode: String = "PHP",
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isActive: Boolean = true,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

/**
 * Domain model for a specific category limit within a budget.
 */
data class BudgetLimit(
    val id: Long = 0,
    val budgetId: Long,
    val category: Category,
    val limitAmount: Double
)

/**
 * Composite model for budget analytics and tracking.
 */
data class BudgetAnalytics(
    val budget: Budget,
    val amountUsed: Double,
    val remainingAmount: Double,
    val percentageUsed: Float,
    val dailyAllowance: Double,
    val averageDailySpending: Double,
    val forecastEndAmount: Double,
    val isOverspent: Boolean,
    val remainingDays: Int,
    val categoryLimits: List<BudgetLimitWithUsage>
)

data class BudgetLimitWithUsage(
    val limit: BudgetLimit,
    val amountUsed: Double,
    val percentageUsed: Float
)
