package com.patflow.app.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * DataStore (Preferences) — Architecture §8.9: app settings (theme, default
 * currency, reminder offset, security toggle) are single-value state, not
 * relational Room records. Preference keys live in core/constants/PreferenceKeys.kt.
 *
 * Full read/write wrapper (typed get/set per key, defaults) lands with the
 * Settings feature — this extension property is the minimal foundation
 * DatabaseModule/DataStoreModule (di/) inject against.
 */
val Context.patFlowDataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(
    name = "patflow_preferences",
)
