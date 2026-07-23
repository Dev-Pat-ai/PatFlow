package com.patflow.app.domain.repository

import com.patflow.app.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

/**
 * Interface for reading and writing user settings and profile foundation (Architecture §8.9).
 */
interface SettingsRepository {
    fun getUserPreferences(): Flow<UserPreferences>
    
    suspend fun setDisplayName(name: String)
    suspend fun setMonthlyBudget(amount: Double?)
    suspend fun setThemeMode(mode: String)
    suspend fun setDynamicColorEnabled(enabled: Boolean)
    suspend fun setPreferredCurrency(code: String)
    suspend fun setDateFormat(format: String)
    suspend fun setFirstDayOfWeek(day: Int)
    suspend fun setHapticFeedbackEnabled(enabled: Boolean)
    suspend fun setNotificationsMasterEnabled(enabled: Boolean)
    suspend fun setReminderOffsets(offsets: Set<Int>)
    suspend fun setNotificationUpcomingEnabled(enabled: Boolean)
    suspend fun setNotificationDueTodayEnabled(enabled: Boolean)
    suspend fun setNotificationOverdueEnabled(enabled: Boolean)
    suspend fun setNotificationPaymentSuccessEnabled(enabled: Boolean)
    suspend fun setNotificationBackupSuccessEnabled(enabled: Boolean)
    suspend fun setQuietHoursEnabled(enabled: Boolean)
    suspend fun setQuietHoursRange(start: String, end: String)
    
    suspend fun restoreAll(preferences: UserPreferences)
}
