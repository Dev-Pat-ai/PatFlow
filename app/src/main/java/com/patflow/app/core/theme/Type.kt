package com.patflow.app.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Design System §3 — Typography.
 *
 * Roboto Flex (variable font) for all UI text. A system-font fallback is used
 * until the variable font assets are added under res/font/ in a later asset
 * pass — swap PatFlowFontFamily for a real FontFamily(Font(...)) then.
 */
val PatFlowFontFamily: FontFamily = FontFamily.Default

val PatFlowTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.25).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp, // Refined from 22sp for better scale
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = PatFlowFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
    ),
)

/**
 * Custom "Amount" type role (Design System §3) — not part of M3's default
 * Typography set, so it's exposed separately rather than mapped onto an
 * existing role. Always paired with tabular figures so amounts align in
 * lists regardless of currency (Design System §11).
 */
val AmountTextStyle = TextStyle(
    fontFamily = PatFlowFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    lineHeight = 28.sp,
    fontFeatureSettings = "tnum", // tabular (lining) figures
)
