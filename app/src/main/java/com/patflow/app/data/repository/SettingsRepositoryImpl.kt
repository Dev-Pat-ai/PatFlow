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
                notificationDueTomorrow = preferences[PreferenceKeys.NOTIF_DUE_TOMORROW] ?: true,
                notificationDueToday = preferences[PreferenceKeys.NOTIF_DUE_TODAY] ?: true,
                notificationOverdue = preferences[PreferenceKeys.NOTIF_OVERDUE] ?: true
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

    override suspend fun setNotificationDueTomorrow(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_DUE_TOMORROW] = enabled }
    }

    override suspend fun setNotificationDueToday(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_DUE_TODAY] = enabled }
    }

    override suspend fun setNotificationOverdue(enabled: Boolean) {
        dataStore.edit { it[PreferenceKeys.NOTIF_OVERDUE] = enabled }
    }
}
