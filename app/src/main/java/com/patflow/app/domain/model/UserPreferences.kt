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
    val notificationDueTomorrow: Boolean = true,
    val notificationDueToday: Boolean = true,
    val notificationOverdue: Boolean = true
)
