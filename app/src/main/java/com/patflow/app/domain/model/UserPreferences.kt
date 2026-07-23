package com.patflow.app.domain.model

/**
 * Aggregated domain model for all user preferences and profile state (Architecture §8.9).
 */
data class UserPreferences(
    val profile: UserProfile = UserProfile(),
    val useDynamicColor: Boolean = true,
    val dateFormat: String = "MM/dd/yyyy",
    val firstDayOfWeek: Int = 1, // 1 = Sunday, 2 = Monday
    val hapticFeedbackEnabled: Boolean = true,
    val notificationsMasterEnabled: Boolean = true,
    val reminderOffsets: Set<Int> = setOf(0, 1, 3), // days before due date
    val notificationUpcomingEnabled: Boolean = true,
    val notificationDueTodayEnabled: Boolean = true,
    val notificationOverdueEnabled: Boolean = true,
    val notificationPaymentSuccessEnabled: Boolean = true,
    val notificationBackupSuccessEnabled: Boolean = true,
    val quietHoursEnabled: Boolean = false,
    val quietHoursStart: String = "22:00",
    val quietHoursEnd: String = "07:00"
)
