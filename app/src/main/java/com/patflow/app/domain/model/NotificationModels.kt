package com.patflow.app.domain.model

import kotlinx.datetime.LocalDateTime

/**
 * Types of notifications supported by PatFlow (Architecture §Phase 8).
 */
enum class NotificationType {
    UPCOMING_BILL,
    DUE_TODAY,
    OVERDUE_BILL,
    RECURRING_GENERATED,
    PAYMENT_SUCCESS,
    BUDGET_ALERT,
    BACKUP_SUCCESS,
    RESTORE_SUCCESS
}

/**
 * Snooze duration options for bill reminders.
 */
enum class SnoozeOption {
    ONE_HOUR,
    TOMORROW,
    NEXT_WEEK
}

/**
 * Domain model for a scheduled reminder record.
 */
data class Reminder(
    val id: Long = 0,
    val billCycleId: Long? = null,
    val incomeSourceId: Long? = null,
    val remindAt: LocalDateTime,
    val isSent: Boolean = false,
    val offsetDays: Int
)
