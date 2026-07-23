package com.patflow.app.core.constants

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

import androidx.datastore.preferences.core.doublePreferencesKey

/**
 * DataStore (Preferences) keys — Architecture §8.9: settings that are
 * single-value app state (theme, default currency, reminder offset, security
 * toggle) live here, NOT in Room.
 */
object PreferenceKeys {
    val USER_DISPLAY_NAME = stringPreferencesKey("user_display_name")
    val MONTHLY_BUDGET = doublePreferencesKey("monthly_budget")
    val THEME_MODE = stringPreferencesKey("theme_mode") // LIGHT / DARK / SYSTEM
    val DYNAMIC_COLOR_ENABLED = booleanPreferencesKey("dynamic_color_enabled")
    val DEFAULT_CURRENCY_CODE = stringPreferencesKey("default_currency_code")
    val DATE_FORMAT = stringPreferencesKey("date_format")
    val FIRST_DAY_OF_WEEK = intPreferencesKey("first_day_of_week")
    val HAPTIC_FEEDBACK_ENABLED = booleanPreferencesKey("haptic_feedback_enabled")
    val NOTIF_MASTER_ENABLED = booleanPreferencesKey("notif_master_enabled")
    val REMINDER_OFFSETS = stringPreferencesKey("reminder_offsets") // Comma separated
    val NOTIF_UPCOMING_ENABLED = booleanPreferencesKey("notif_upcoming_enabled")
    val NOTIF_DUE_TODAY_ENABLED = booleanPreferencesKey("notif_due_today_enabled")
    val NOTIF_OVERDUE_ENABLED = booleanPreferencesKey("notif_overdue_enabled")
    val NOTIF_PAYMENT_SUCCESS_ENABLED = booleanPreferencesKey("notif_payment_success_enabled")
    val NOTIF_BACKUP_SUCCESS_ENABLED = booleanPreferencesKey("notif_backup_success_enabled")
    val QUIET_HOURS_ENABLED = booleanPreferencesKey("quiet_hours_enabled")
    val QUIET_HOURS_START = stringPreferencesKey("quiet_hours_start")
    val QUIET_HOURS_END = stringPreferencesKey("quiet_hours_end")
    
    val DEFAULT_REMINDER_OFFSET_DAYS = intPreferencesKey("default_reminder_offset_days")
    val SECURITY_LOCK_ENABLED = booleanPreferencesKey("security_lock_enabled")
    val AUTO_LOCK_TIMEOUT_MINUTES = intPreferencesKey("auto_lock_timeout_minutes")
}
