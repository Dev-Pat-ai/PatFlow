package com.patflow.app.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.patflow.app.core.constants.PreferenceKeys
import com.patflow.app.domain.model.ThemeMode
import com.patflow.app.domain.model.UserPreferences
import com.patflow.app.domain.model.UserProfile
import com.patflow.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * DataStore-backed implementation of [SettingsRepository] (Architecture §9).
 */
class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    override fun getUserPreferences(): Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { preferences ->
            val themeMode = try {
                ThemeMode.valueOf(preferences[PreferenceKeys.THEME_MODE] ?: ThemeMode.SYSTEM.name)
            } catch (e: Exception) {
                ThemeMode.SYSTEM
            }

            UserPreferences(
                profile = UserProfile(
                    displayName = preferences[PreferenceKeys.USER_DISPLAY_NAME] ?: "",
                    monthlyBudget = preferences[PreferenceKeys.MONTHLY_BUDGET],
                    preferredCurrency = preferences[PreferenceKeys.DEFAULT_CURRENCY_CODE] ?: "PHP",
                    preferredTheme = themeMode
                ),
                useDynamicColor = preferences[PreferenceKeys.DYNAMIC_COLOR_ENABLED] ?: true,
                dateFormat = preferences[PreferenceKeys.DATE_FORMAT] ?: "MM/dd/yyyy",
                firstDayOfWeek = preferences[PreferenceKeys.FIRST_DAY_OF_WEEK] ?: 1,
                hapticFeedbackEnabled = preferences[PreferenceKeys.HAPTIC_FEEDBACK_ENABLED] ?: true,
                notificationsMasterEnabled = preferences[PreferenceKeys.NOTIF_MASTER_ENABLED] ?: true,
                reminderOffsets = preferences[PreferenceKeys.REMINDER_OFFSETS]?.split(",")?.mapNotNull { it.toIntOrNull() }?.toSet() ?: setOf(0, 1, 3),
                notificationUpcomingEnabled = preferences[PreferenceKeys.NOTIF_UPCOMING_ENABLED] ?: true,
                notificationDueTodayEnabled = preferences[PreferenceKeys.NOTIF_DUE_TODAY_ENABLED] ?: true,
                notificationOverdueEnabled = preferences[PreferenceKeys.NOTIF_OVERDUE_ENABLED] ?: true,
                notificationPaymentSuccessEnabled = preferences[PreferenceKeys.NOTIF_PAYMENT_SUCCESS_ENABLED] ?: true,
                notificationBackupSuccessEnabled = preferences[PreferenceKeys.NOTIF_BACKUP_SUCCESS_ENABLED] ?: true,
                quietHoursEnabled = preferences[PreferenceKeys.QUIET_HOURS_ENABLED] ?: false,
                quietHoursStart = preferences[PreferenceKeys.QUIET_HOURS_START] ?: "22:00",
                quietHoursEnd = preferences[PreferenceKeys.QUIET_HOURS_END] ?: "07:00"
            )
        }

    override suspend fun setDisplayName(name: String) {
        dataStore.edit { it[PreferenceKeys.USER_DISPLAY_NAME] = name }
    }

    override suspend fun setMonthlyBudget(amount: Double?) {
        dataStore.edit { 
            if (amount != null) it[PreferenceKeys.MONTHLY_BUDGET] = amount
            else it.remove(PreferenceKeys.MONTHLY_BUDGET)
        }
    }

    override suspend fun setThemeMode(mode: String) {
        dataStore.edit { it[PreferenceKeys.THEME_MODE] = mode }
    }

    override suspend fun setDynamicColorEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.DYNAMIC_COLOR_ENABLED] = enabled }
    }

    override suspend fun setPreferredCurrency(code: String) {
        dataStore.edit { it[PreferenceKeys.DEFAULT_CURRENCY_CODE] = code }
    }

    override suspend fun setDateFormat(format: String) {
        dataStore.edit { it[PreferenceKeys.DATE_FORMAT] = format }
    }

    override suspend fun setFirstDayOfWeek(day: Int) {
        dataStore.edit { it[PreferenceKeys.FIRST_DAY_OF_WEEK] = day }
    }

    override suspend fun setHapticFeedbackEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.HAPTIC_FEEDBACK_ENABLED] = enabled }
    }

    override suspend fun setNotificationsMasterEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_MASTER_ENABLED] = enabled }
    }

    override suspend fun setReminderOffsets(offsets: Set<Int>) {
        dataStore.edit { it[PreferenceKeys.REMINDER_OFFSETS] = offsets.joinToString(",") }
    }

    override suspend fun setNotificationUpcomingEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_UPCOMING_ENABLED] = enabled }
    }

    override suspend fun setNotificationDueTodayEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_DUE_TODAY_ENABLED] = enabled }
    }

    override suspend fun setNotificationOverdueEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_OVERDUE_ENABLED] = enabled }
    }

    override suspend fun setNotificationPaymentSuccessEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_PAYMENT_SUCCESS_ENABLED] = enabled }
    }

    override suspend fun setNotificationBackupSuccessEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_BACKUP_SUCCESS_ENABLED] = enabled }
    }

    override suspend fun setQuietHoursEnabled(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.QUIET_HOURS_ENABLED] = enabled }
    }

    override suspend fun setQuietHoursRange(start: String, end: String) {
        dataStore.edit { 
            it[PreferenceKeys.QUIET_HOURS_START] = start
            it[PreferenceKeys.QUIET_HOURS_END] = end
        }
    }

    override suspend fun restoreAll(preferences: UserPreferences) {
        dataStore.edit {
            it[PreferenceKeys.USER_DISPLAY_NAME] = preferences.profile.displayName
            if (preferences.profile.monthlyBudget != null) {
                it[PreferenceKeys.MONTHLY_BUDGET] = preferences.profile.monthlyBudget
            } else {
                it.remove(PreferenceKeys.MONTHLY_BUDGET)
            }
            it[PreferenceKeys.THEME_MODE] = preferences.profile.preferredTheme.name
            it[PreferenceKeys.DYNAMIC_COLOR_ENABLED] = preferences.useDynamicColor
            it[PreferenceKeys.DEFAULT_CURRENCY_CODE] = preferences.profile.preferredCurrency
            it[PreferenceKeys.DATE_FORMAT] = preferences.dateFormat
            it[PreferenceKeys.FIRST_DAY_OF_WEEK] = preferences.firstDayOfWeek
            it[PreferenceKeys.HAPTIC_FEEDBACK_ENABLED] = preferences.hapticFeedbackEnabled
            it[PreferenceKeys.NOTIF_MASTER_ENABLED] = preferences.notificationsMasterEnabled
            it[PreferenceKeys.REMINDER_OFFSETS] = preferences.reminderOffsets.joinToString(",")
            it[PreferenceKeys.NOTIF_UPCOMING_ENABLED] = preferences.notificationUpcomingEnabled
            it[PreferenceKeys.NOTIF_DUE_TODAY_ENABLED] = preferences.notificationDueTodayEnabled
            it[PreferenceKeys.NOTIF_OVERDUE_ENABLED] = preferences.notificationOverdueEnabled
            it[PreferenceKeys.NOTIF_PAYMENT_SUCCESS_ENABLED] = preferences.notificationPaymentSuccessEnabled
            it[PreferenceKeys.NOTIF_BACKUP_SUCCESS_ENABLED] = preferences.notificationBackupSuccessEnabled
            it[PreferenceKeys.QUIET_HOURS_ENABLED] = preferences.quietHoursEnabled
            it[PreferenceKeys.QUIET_HOURS_START] = preferences.quietHoursStart
            it[PreferenceKeys.QUIET_HOURS_END] = preferences.quietHoursEnd
        }
    }
}
