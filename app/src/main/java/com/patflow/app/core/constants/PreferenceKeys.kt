package com.patflow.app.core.constants

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * DataStore (Preferences) keys — Architecture §8.9: settings that are
 * single-value app state (theme, default currency, reminder offset, security
 * toggle) live here, NOT in Room.
 */
object PreferenceKeys {
    val THEME_MODE = stringPreferencesKey("theme_mode") // LIGHT / DARK / SYSTEM
    val DYNAMIC_COLOR_ENABLED = booleanPreferencesKey("dynamic_color_enabled")
    val DEFAULT_CURRENCY_CODE = stringPreferencesKey("default_currency_code")
    val DEFAULT_REMINDER_OFFSET_DAYS = intPreferencesKey("default_reminder_offset_days")
    val SECURITY_LOCK_ENABLED = booleanPreferencesKey("security_lock_enabled")
    val AUTO_LOCK_TIMEOUT_MINUTES = intPreferencesKey("auto_lock_timeout_minutes")
}
