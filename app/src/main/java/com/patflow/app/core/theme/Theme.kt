package com.patflow.app.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Design System §2 — Material You (Dynamic Color).
 *
 * Default ON for API 31+ (derives the full role set from the user's
 * wallpaper). Falls back to the static Ink-Cobalt-seed palette (Color.kt)
 * on API < 31, or when the user disables Dynamic Color in Settings
 * (FR-11.1) — [useDynamicColor] is threaded from that setting once the
 * Settings feature exists; it defaults to true here.
 *
 * NOTE: status colors and category colors are intentionally NOT part of the
 * MaterialTheme.colorScheme swap — they are fixed semantic tokens (see
 * PatFlowStatusColors / PatFlowCategoryColors in Color.kt) accessed directly
 * by components, never derived from wallpaper.
 */
@Composable
fun PatFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val dynamicColorSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colorScheme = when {
        useDynamicColor && dynamicColorSupported && darkTheme -> dynamicDarkColorScheme(context)
        useDynamicColor && dynamicColorSupported && !darkTheme -> dynamicLightColorScheme(context)
        darkTheme -> darkColorScheme(
            primary = PatFlowDarkColors.primary,
            onPrimary = PatFlowDarkColors.onPrimary,
            primaryContainer = PatFlowDarkColors.primaryContainer,
            onPrimaryContainer = PatFlowDarkColors.onPrimaryContainer,
            secondary = PatFlowDarkColors.secondary,
            secondaryContainer = PatFlowDarkColors.secondaryContainer,
            tertiary = PatFlowDarkColors.tertiary,
            tertiaryContainer = PatFlowDarkColors.tertiaryContainer,
            onTertiaryContainer = PatFlowDarkColors.onTertiaryContainer,
            error = PatFlowDarkColors.error,
            errorContainer = PatFlowDarkColors.errorContainer,
            background = PatFlowDarkColors.background,
            onBackground = PatFlowDarkColors.onBackground,
            surface = PatFlowDarkColors.surface,
            surfaceVariant = PatFlowDarkColors.surfaceVariant,
            onSurfaceVariant = PatFlowDarkColors.onSurfaceVariant,
            outline = PatFlowDarkColors.outline,
            outlineVariant = PatFlowDarkColors.outlineVariant,
        )
        else -> lightColorScheme(
            primary = PatFlowLightColors.primary,
            onPrimary = PatFlowLightColors.onPrimary,
            primaryContainer = PatFlowLightColors.primaryContainer,
            onPrimaryContainer = PatFlowLightColors.onPrimaryContainer,
            secondary = PatFlowLightColors.secondary,
            secondaryContainer = PatFlowLightColors.secondaryContainer,
            tertiary = PatFlowLightColors.tertiary,
            tertiaryContainer = PatFlowLightColors.tertiaryContainer,
            onTertiaryContainer = PatFlowLightColors.onTertiaryContainer,
            error = PatFlowLightColors.error,
            errorContainer = PatFlowLightColors.errorContainer,
            background = PatFlowLightColors.background,
            onBackground = PatFlowLightColors.onBackground,
            surface = PatFlowLightColors.surface,
            surfaceVariant = PatFlowLightColors.surfaceVariant,
            onSurfaceVariant = PatFlowLightColors.onSurfaceVariant,
            outline = PatFlowLightColors.outline,
            outlineVariant = PatFlowLightColors.outlineVariant,
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PatFlowTypography,
        shapes = PatFlowMaterialShapes,
        content = content,
    )
}

/** Convenience accessor for the fixed status-color set matching the current theme brightness. */
@Composable
fun patFlowStatusColors(): StatusColors = if (isSystemInDarkTheme()) PatFlowStatusColors.Dark else PatFlowStatusColors.Light

/** Convenience accessor for the fixed category-color set matching the current theme brightness. */
@Composable
fun patFlowCategoryColors(): CategoryColors = if (isSystemInDarkTheme()) PatFlowCategoryColors.Dark else PatFlowCategoryColors.Light
