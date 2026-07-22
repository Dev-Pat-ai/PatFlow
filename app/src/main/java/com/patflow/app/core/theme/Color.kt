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
    val secondaryContainer = Color(0xFFE0E1F9)
    val tertiary = Color(0xFF8C5A00)
    val tertiaryContainer = Color(0xFFFFDEA6)
    val onTertiaryContainer = Color(0xFF2B1900)
    val error = Color(0xFFBA1A1A)
    val errorContainer = Color(0xFFFFDAD6)
    val background = Color(0xFFFBF8FF)
    val onBackground = Color(0xFF1A1B21)
    val surface = Color(0xFFFBF8FF)
    val surfaceVariant = Color(0xFFE3E1EC)
    val onSurfaceVariant = Color(0xFF46464F)
    val outline = Color(0xFF767680)
    val outlineVariant = Color(0xFFC7C5D0)
}

// ---- Dark theme core roles (Design System §1.3) ----
object PatFlowDarkColors {
    val primary = Color(0xFFB7C3FF)
    val onPrimary = Color(0xFF132057)
    val primaryContainer = Color(0xFF28376E)
    val onPrimaryContainer = Color(0xFFDCE1FF)
    val secondary = Color(0xFFC4C5DD)
    val secondaryContainer = Color(0xFF434659)
    val tertiary = Color(0xFFFFB945)
    val tertiaryContainer = Color(0xFF6B4700)
    val onTertiaryContainer = Color(0xFFFFDEA6)
    val error = Color(0xFFFFB4AB)
    val errorContainer = Color(0xFF93000A)
    val background = Color(0xFF121318)
    val onBackground = Color(0xFFE4E1E9)
    val surface = Color(0xFF121318)
    val surfaceVariant = Color(0xFF46464F)
    val onSurfaceVariant = Color(0xFFC7C5D0)
    val outline = Color(0xFF90909A)
    val outlineVariant = Color(0xFF46464F)
}

/**
 * Status colors (Design System §1.4). Color is NEVER the sole status signal —
 * every usage of these pairs with an icon + label. These are FIXED regardless
 * of Dynamic Color (Design System §2) since semantic meaning can't shift with
 * wallpaper.
 */
data class StatusColorPair(val onColor: Color, val containerColor: Color)

object PatFlowStatusColors {
    object Light {
        val paid = StatusColorPair(Color(0xFF2E8B57), Color(0xFFD7F0E2))
        val partiallyPaid = StatusColorPair(Color(0xFF8C5A00), Color(0xFFFFE9C2))
        val unpaid = StatusColorPair(Color(0xFF3D4E85), Color(0xFFDCE1FF))
        val overdue = StatusColorPair(Color(0xFFBA1A1A), Color(0xFFFFDAD6))
    }
    object Dark {
        val paid = StatusColorPair(Color(0xFF8FD9A8), Color(0xFF0F3D22))
        val partiallyPaid = StatusColorPair(Color(0xFFFFB945), Color(0xFF4A3200))
        val unpaid = StatusColorPair(Color(0xFFB7C3FF), Color(0xFF28376E))
        val overdue = StatusColorPair(Color(0xFFFFB4AB), Color(0xFF93000A))
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

object PatFlowCategoryColors {
    object Light {
        val electricity = CategoryColorPair(Color(0xFF8C5A00), Color(0xFFFFE9C2))
        val water = CategoryColorPair(Color(0xFF00658F), Color(0xFFC3E8FF))
        val internet = CategoryColorPair(Color(0xFF3D4E85), Color(0xFFDCE1FF))
        val rent = CategoryColorPair(Color(0xFF6B4F1C), Color(0xFFF3E0BC))
        val phone = CategoryColorPair(Color(0xFF5C5F00), Color(0xFFE2E792))
        val insurance = CategoryColorPair(Color(0xFF00658F), Color(0xFFC3E8FF))
        val tuition = CategoryColorPair(Color(0xFF7D2E68), Color(0xFFFFD8EC))
        val subscription = CategoryColorPair(Color(0xFF8C3B00), Color(0xFFFFDBC7))
        // Loan: Brown — distinct from the Overdue/error red family (Phase 0 lock resolution).
        val loan = CategoryColorPair(Color(0xFF8B5000), Color(0xFFFFDEAD))
        // Savings: Teal — distinct from the Paid/Sea Green family (Phase 0 lock resolution).
        val savings = CategoryColorPair(Color(0xFF006874), Color(0xFF97F0FF))
        val hoaFees = CategoryColorPair(Color(0xFF5B5D72), Color(0xFFE0E1F9))
    }
    object Dark {
        val electricity = CategoryColorPair(Color(0xFFFFB945), Color(0xFF4A3200))
        val water = CategoryColorPair(Color(0xFF89CFF4), Color(0xFF003548))
        val internet = CategoryColorPair(Color(0xFFB7C3FF), Color(0xFF28376E))
        val rent = CategoryColorPair(Color(0xFFE3C57F), Color(0xFF3D2C00))
        val phone = CategoryColorPair(Color(0xFFC6CC5E), Color(0xFF2E3000))
        val insurance = CategoryColorPair(Color(0xFF89CFF4), Color(0xFF003548))
        val tuition = CategoryColorPair(Color(0xFFF2A6D7), Color(0xFF4B1339))
        val subscription = CategoryColorPair(Color(0xFFFFB68B), Color(0xFF4A2100))
        val loan = CategoryColorPair(Color(0xFFFFB870), Color(0xFF5C3900))
        val savings = CategoryColorPair(Color(0xFF4FD8EB), Color(0xFF00363D))
        val hoaFees = CategoryColorPair(Color(0xFFC4C5DD), Color(0xFF434659))
    }
}
