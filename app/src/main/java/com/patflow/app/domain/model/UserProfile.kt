package com.patflow.app.domain.model

/**
 * Domain model for a local user profile (Architecture §1.11 / Phase 7A).
 * Establish foundation for future personalization and cloud synchronization.
 */
data class UserProfile(
    val displayName: String = "",
    val monthlyBudget: Double? = null,
    val preferredCurrency: String = "PHP",
    val preferredTheme: ThemeMode = ThemeMode.SYSTEM,
    val avatarPlaceholder: String? = null // Future: Profile Avatar
)

/** Theme selection options. */
enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}
