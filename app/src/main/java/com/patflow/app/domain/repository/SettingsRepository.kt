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
    suspend fun setNotificationDueTomorrow(enabled: Boolean)
    suspend fun setNotificationDueToday(enabled: Boolean)
    suspend fun setNotificationOverdue(enabled: Boolean)
}
