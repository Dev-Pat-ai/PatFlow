package com.patflow.app.domain.usecase.settings

import com.patflow.app.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Use case for updating individual user preferences.
 */
class UpdateUserPreferenceUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend fun setDisplayName(name: String) = repository.setDisplayName(name)
    suspend fun setMonthlyBudget(amount: Double?) = repository.setMonthlyBudget(amount)
    suspend fun setThemeMode(mode: String) = repository.setThemeMode(mode)
    suspend fun setDynamicColorEnabled(enabled: Boolean) = repository.setDynamicColorEnabled(enabled)
    suspend fun setPreferredCurrency(code: String) = repository.setPreferredCurrency(code)
    suspend fun setDateFormat(format: String) = repository.setDateFormat(format)
    suspend fun setFirstDayOfWeek(day: Int) = repository.setFirstDayOfWeek(day)
    suspend fun setHapticFeedbackEnabled(enabled: Boolean) = repository.setHapticFeedbackEnabled(enabled)
    suspend fun setNotificationsMasterEnabled(enabled: Boolean) = repository.setNotificationsMasterEnabled(enabled)
    suspend fun setNotificationUpcomingEnabled(enabled: Boolean) = repository.setNotificationUpcomingEnabled(enabled)
    suspend fun setNotificationDueTodayEnabled(enabled: Boolean) = repository.setNotificationDueTodayEnabled(enabled)
    suspend fun setNotificationOverdueEnabled(enabled: Boolean) = repository.setNotificationOverdueEnabled(enabled)
    suspend fun setNotificationPaymentSuccessEnabled(enabled: Boolean) = repository.setNotificationPaymentSuccessEnabled(enabled)
    suspend fun setNotificationBackupSuccessEnabled(enabled: Boolean) = repository.setNotificationBackupSuccessEnabled(enabled)
    suspend fun setQuietHoursEnabled(enabled: Boolean) = repository.setQuietHoursEnabled(enabled)
    suspend fun setQuietHoursRange(start: String, end: String) = repository.setQuietHoursRange(start, end)
}
