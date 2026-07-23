package com.patflow.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.UserPreferences
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import com.patflow.app.domain.usecase.settings.UpdateUserPreferenceUseCase
import com.patflow.app.domain.model.NotificationType
import com.patflow.app.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Settings screen (Architecture §6).
 * Manages user profile and application preferences.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    getUserSettingsUseCase: GetUserSettingsUseCase,
    private val updatePreference: UpdateUserPreferenceUseCase,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    val uiState: StateFlow<UserPreferences?> = getUserSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateDisplayName(name: String) {
        viewModelScope.launch { updatePreference.setDisplayName(name) }
    }

    fun updateMonthlyBudget(amount: Double?) {
        viewModelScope.launch { updatePreference.setMonthlyBudget(amount) }
    }

    fun updateThemeMode(mode: String) {
        viewModelScope.launch { updatePreference.setThemeMode(mode) }
    }

    fun updateDynamicColor(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setDynamicColorEnabled(enabled) }
    }

    fun updatePreferredCurrency(code: String) {
        viewModelScope.launch { updatePreference.setPreferredCurrency(code) }
    }

    fun updateDateFormat(format: String) {
        viewModelScope.launch { updatePreference.setDateFormat(format) }
    }

    fun updateFirstDayOfWeek(day: Int) {
        viewModelScope.launch { updatePreference.setFirstDayOfWeek(day) }
    }

    fun updateHapticFeedback(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setHapticFeedbackEnabled(enabled) }
    }

    fun updateNotifMaster(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setNotificationsMasterEnabled(enabled) }
    }

    fun updateNotifUpcoming(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setNotificationUpcomingEnabled(enabled) }
    }

    fun updateNotifDueToday(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setNotificationDueTodayEnabled(enabled) }
    }

    fun updateNotifOverdue(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setNotificationOverdueEnabled(enabled) }
    }

    fun updateNotifPaymentSuccess(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setNotificationPaymentSuccessEnabled(enabled) }
    }

    fun updateNotifBackupSuccess(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setNotificationBackupSuccessEnabled(enabled) }
    }

    fun updateQuietHoursEnabled(enabled: Boolean) {
        viewModelScope.launch { updatePreference.setQuietHoursEnabled(enabled) }
    }

    fun testNotification() {
        viewModelScope.launch {
            notificationRepository.showSystemNotification(
                NotificationType.RECURRING_GENERATED,
                "Test Notification",
                "This is a sample notification from PatFlow."
            )
        }
    }
}
