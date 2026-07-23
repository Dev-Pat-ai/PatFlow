package com.patflow.app.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * Domain model representing a savings goal (Architecture §1.13 / Phase 11).
 */
data class SavingsGoal(
    val id: Long = 0,
    val name: String,
    val targetAmount: Double,
    val currencyCode: String = "PHP",
    val currentAmount: Double = 0.0,
    val targetDate: LocalDate? = null,
    val iconKey: String,
    val colorHex: String,
    val notes: String? = null,
    val priority: Int = 0,
    val isCompleted: Boolean = false,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false,
    val createdAt: LocalDateTime
)

/**
 * Domain model for a contribution to a savings goal.
 */
data class SavingsContribution(
    val id: Long = 0,
    val savingsGoalId: Long,
    val amount: Double,
    val contributionDate: LocalDate,
    val note: String? = null,
    val createdAt: LocalDateTime
)

/**
 * Composite model for goal analytics and tracking.
 */
data class SavingsGoalAnalytics(
    val goal: SavingsGoal,
    val remainingAmount: Double,
    val progressPercentage: Float,
    val estimatedCompletionDate: LocalDate?,
    val monthlyRequiredSavings: Double?
)
