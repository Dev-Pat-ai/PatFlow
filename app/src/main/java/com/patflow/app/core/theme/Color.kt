package com.patflow.app.core.theme

import androidx.compose.ui.graphics.Color

/**
 * Design System §0 — Brand Direction (seed palette) and §1 — Color Palette.
 * These are the STATIC FALLBACK values used below API 31 or when the user
 * disables Dynamic Color in Settings (Architecture §10). Dynamic (Material You)
 * schemes are generated at runtime in Theme.kt and are NOT defined here.
 */

// ---- Seed palette (Design System §0) ----
val InkCobalt = Color(0xFF2B3A67)
val AmberFlow = Color(0xFFF2A93B)
val SeaGreen = Color(0xFF2E8B57)
val CrimsonAlert = Color(0xFFD2444F)

// ---- Light theme core roles (Design System §1.2) ----
object PatFlowLightColors {
    val primary = Color(0xFF3D4E85)
    val onPrimary = Color(0xFFFFFFFF)
    val primaryContainer = Color(0xFFDCE1FF)
    val onPrimaryContainer = Color(0xFF141B36)
    val secondary = Color(0xFF5B5D72)
    val onSecondary = Color(0xFFFFFFFF)
    val secondaryContainer = Color(0xFFE0E1F9)
    val onSecondaryContainer = Color(0xFF181A2C)
    val tertiary = Color(0xFF8C5A00)
    val onTertiary = Color(0xFFFFFFFF)
    val tertiaryContainer = Color(0xFFFFDEA6)
    val onTertiaryContainer = Color(0xFF2B1900)
    val error = Color(0xFFBA1A1A)
    val onError = Color(0xFFFFFFFF)
    val errorContainer = Color(0xFFFFDAD6)
    val onErrorContainer = Color(0xFF410002)
    val background = Color(0xFFFEF7FF) // Refined subtle surface
    val onBackground = Color(0xFF1B1B1F)
    val surface = Color(0xFFFEF7FF)
    val onSurface = Color(0xFF1B1B1F)
    val surfaceVariant = Color(0xFFE3E1EC)
    val onSurfaceVariant = Color(0xFF46464F)
    val outline = Color(0xFF767680)
    val outlineVariant = Color(0xFFC7C5D0)
    
    // New M3 Roles for containers
    val surfaceContainerLowest = Color(0xFFFFFFFF)
    val surfaceContainerLow = Color(0xFFF7F2FA)
    val surfaceContainer = Color(0xFFF3EDF7)
    val surfaceContainerHigh = Color(0xFFECE6F0)
    val surfaceContainerHighest = Color(0xFFE6E0E9)
}

// ---- Dark theme core roles (Design System §1.3) ----
object PatFlowDarkColors {
    val primary = Color(0xFFB7C3FF)
    val onPrimary = Color(0xFF03265D)
    val primaryContainer = Color(0xFF233A6B)
    val onPrimaryContainer = Color(0xFFDCE1FF)
    val secondary = Color(0xFFC4C5DD)
    val onSecondary = Color(0xFF2C2F42)
    val secondaryContainer = Color(0xFF434659)
    val onSecondaryContainer = Color(0xFFE0E1F9)
    val tertiary = Color(0xFFFFB945)
    val onTertiary = Color(0xFF4A2D00)
    val tertiaryContainer = Color(0xFF6B4200)
    val onTertiaryContainer = Color(0xFFFFDEA6)
    val error = Color(0xFFFFB4AB)
    val onError = Color(0xFF690005)
    val errorContainer = Color(0xFF93000A)
    val onErrorContainer = Color(0xFFFFDAD6)
    val background = Color(0xFF121318)
    val onBackground = Color(0xFFE4E1E9)
    val surface = Color(0xFF121318)
    val onSurface = Color(0xFFE4E1E9)
    val surfaceVariant = Color(0xFF46464F)
    val onSurfaceVariant = Color(0xFFC7C5D0)
    val outline = Color(0xFF90909A)
    val outlineVariant = Color(0xFF46464F)

    // New M3 Roles for containers
    val surfaceContainerLowest = Color(0xFF0C0E13)
    val surfaceContainerLow = Color(0xFF1A1C21)
    val surfaceContainer = Color(0xFF1E2025)
    val surfaceContainerHigh = Color(0xFF292A2F)
    val surfaceContainerHighest = Color(0xFF33353A)
}

/**
 * Status colors (Design System §1.4). Color is NEVER the sole status signal —
 * every usage of these pairs with an icon + label. These are FIXED regardless
 * of Dynamic Color (Design System §2) since semantic meaning can't shift with
 * wallpaper.
 */
data class StatusColorPair(val onColor: Color, val containerColor: Color)

interface StatusColors {
    val paid: StatusColorPair
    val partiallyPaid: StatusColorPair
    val unpaid: StatusColorPair
    val overdue: StatusColorPair
}

object PatFlowStatusColors {
    object Light : StatusColors {
        override val paid = StatusColorPair(Color(0xFF2E8B57), Color(0xFFD7F0E2))
        override val partiallyPaid = StatusColorPair(Color(0xFF8C5A00), Color(0xFFFFE9C2))
        override val unpaid = StatusColorPair(Color(0xFF3D4E85), Color(0xFFDCE1FF))
        override val overdue = StatusColorPair(Color(0xFFBA1A1A), Color(0xFFFFDAD6))
    }
    object Dark : StatusColors {
        override val paid = StatusColorPair(Color(0xFF8FD9A8), Color(0xFF0F3D22))
        override val partiallyPaid = StatusColorPair(Color(0xFFFFB945), Color(0xFF4A3200))
        override val unpaid = StatusColorPair(Color(0xFFB7C3FF), Color(0xFF28376E))
        override val overdue = StatusColorPair(Color(0xFFFFB4AB), Color(0xFF93000A))
    }
}

/**
 * Category branding (Design System §10). Fixed per predefined category, never
 * user-editable, never reused for status meaning (Governance §17).
 *
 * Loan -> Brown and Savings -> Teal per the Phase 0 lock decision: every
 * predefined category now has a color family distinct from both the status
 * colors above and every other category, resolving the Loan/Overdue and
 * Savings/Paid collisions flagged during Phase 0 review.
 */
data class CategoryColorPair(val onColor: Color, val containerColor: Color)

interface CategoryColors {
    val electricity: CategoryColorPair
    val water: CategoryColorPair
    val internet: CategoryColorPair
    val rent: CategoryColorPair
    val phone: CategoryColorPair
    val insurance: CategoryColorPair
    val tuition: CategoryColorPair
    val subscription: CategoryColorPair
    val loan: CategoryColorPair
    val savings: CategoryColorPair
    val hoaFees: CategoryColorPair

    // Income categories
    val salary: CategoryColorPair
    val freelance: CategoryColorPair
    val business: CategoryColorPair
    val allowance: CategoryColorPair
    val bonus: CategoryColorPair
    val commission: CategoryColorPair
    val investment: CategoryColorPair
    val cashback: CategoryColorPair
    val refund: CategoryColorPair
    val gift: CategoryColorPair
    val other: CategoryColorPair
}

object PatFlowCategoryColors {
    object Light : CategoryColors {
        override val electricity = CategoryColorPair(Color(0xFF8C5A00), Color(0xFFFFE9C2))
        override val water = CategoryColorPair(Color(0xFF00658F), Color(0xFFC3E8FF))
        override val internet = CategoryColorPair(Color(0xFF3D4E85), Color(0xFFDCE1FF))
        override val rent = CategoryColorPair(Color(0xFF6B4F1C), Color(0xFFF3E0BC))
        override val phone = CategoryColorPair(Color(0xFF5C5F00), Color(0xFFE2E792))
        override val insurance = CategoryColorPair(Color(0xFF00658F), Color(0xFFC3E8FF))
        override val tuition = CategoryColorPair(Color(0xFF7D2E68), Color(0xFFFFD8EC))
        override val subscription = CategoryColorPair(Color(0xFF8C3B00), Color(0xFFFFDBC7))
        override val loan = CategoryColorPair(Color(0xFF8B5000), Color(0xFFFFDEAD))
        override val savings = CategoryColorPair(Color(0xFF006874), Color(0xFF97F0FF))
        override val hoaFees = CategoryColorPair(Color(0xFF5B5D72), Color(0xFFE0E1F9))

        override val salary = CategoryColorPair(Color(0xFF2E8B57), Color(0xFFD7F0E2))
        override val freelance = CategoryColorPair(Color(0xFF00658F), Color(0xFFC3E8FF))
        override val business = CategoryColorPair(Color(0xFF8C5A00), Color(0xFFFFE9C2))
        override val allowance = CategoryColorPair(Color(0xFF3D4E85), Color(0xFFDCE1FF))
        override val bonus = CategoryColorPair(Color(0xFFBA1A1A), Color(0xFFFFDAD6))
        override val commission = CategoryColorPair(Color(0xFF6B4F1C), Color(0xFFF3E0BC))
        override val investment = CategoryColorPair(Color(0xFF7D2E68), Color(0xFFFFD8EC))
        override val cashback = CategoryColorPair(Color(0xFF8C3B00), Color(0xFFFFDBC7))
        override val refund = CategoryColorPair(Color(0xFF5B5D72), Color(0xFFE0E1F9))
        override val gift = CategoryColorPair(Color(0xFF8C5A00), Color(0xFFFFE9C2))
        override val other = CategoryColorPair(Color(0xFF46464F), Color(0xFFE3E1EC))
    }
    object Dark : CategoryColors {
        override val electricity = CategoryColorPair(Color(0xFFFFB945), Color(0xFF4A3200))
        override val water = CategoryColorPair(Color(0xFF89CFF4), Color(0xFF003548))
        override val internet = CategoryColorPair(Color(0xFFB7C3FF), Color(0xFF28376E))
        override val rent = CategoryColorPair(Color(0xFFE3C57F), Color(0xFF3D2C00))
        override val phone = CategoryColorPair(Color(0xFFC6CC5E), Color(0xFF2E3000))
        override val insurance = CategoryColorPair(Color(0xFF89CFF4), Color(0xFF003548))
        override val tuition = CategoryColorPair(Color(0xFFF2A6D7), Color(0xFF4B1339))
        override val subscription = CategoryColorPair(Color(0xFFFFB68B), Color(0xFF4A2100))
        override val loan = CategoryColorPair(Color(0xFFFFB870), Color(0xFF5C3900))
        override val savings = CategoryColorPair(Color(0xFF4FD8EB), Color(0xFF00363D))
        override val hoaFees = CategoryColorPair(Color(0xFFC4C5DD), Color(0xFF434659))

        override val salary = CategoryColorPair(Color(0xFF8FD9A8), Color(0xFF0F3D22))
        override val freelance = CategoryColorPair(Color(0xFF89CFF4), Color(0xFF003548))
        override val business = CategoryColorPair(Color(0xFFFFB945), Color(0xFF4A3200))
        override val allowance = CategoryColorPair(Color(0xFFB7C3FF), Color(0xFF28376E))
        override val bonus = CategoryColorPair(Color(0xFFFFB4AB), Color(0xFF93000A))
        override val commission = CategoryColorPair(Color(0xFFE3C57F), Color(0xFF3D2C00))
        override val investment = CategoryColorPair(Color(0xFFF2A6D7), Color(0xFF4B1339))
        override val cashback = CategoryColorPair(Color(0xFFFFB68B), Color(0xFF4A2100))
        override val refund = CategoryColorPair(Color(0xFFC4C5DD), Color(0xFF434659))
        override val gift = CategoryColorPair(Color(0xFFFFB945), Color(0xFF4A3200))
        override val other = CategoryColorPair(Color(0xFFC7C5D0), Color(0xFF46464F))
    }
}
