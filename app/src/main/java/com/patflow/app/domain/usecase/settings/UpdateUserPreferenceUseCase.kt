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
    suspend fun setNotificationDueTomorrow(enabled: Boolean) = repository.setNotificationDueTomorrow(enabled)
    suspend fun setNotificationDueToday(enabled: Boolean) = repository.setNotificationDueToday(enabled)
    suspend fun setNotificationOverdue(enabled: Boolean) = repository.setNotificationOverdue(enabled)
}
