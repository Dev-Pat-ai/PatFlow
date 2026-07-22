package com.patflow.app.core.constants

/** App-wide constants that aren't user preferences (those live in PreferenceKeys / DataStore). */
object AppConstants {
    const val DATABASE_NAME = "patflow.db"
    const val DEFAULT_CURRENCY_CODE = "PHP" // Architecture §1.15 / FR-15.1
    const val RECENT_SEARCH_LIMIT = 10 // FR-17.2
    const val DEEP_LINK_SCHEME = "patflow" // Architecture §6
}
