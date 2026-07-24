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

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.ThemeMode
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {
    val themeSettings = getUserSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}

/**
 * Design System §2 — Material You (Dynamic Color).
 * Updated to consume live settings for theme and dynamic color.
 */
@Composable
fun PatFlowTheme(
    viewModel: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val settings by viewModel.themeSettings.collectAsState()
    val context = LocalContext.current
    
    val darkTheme = when (settings?.profile?.preferredTheme) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        else -> isSystemInDarkTheme()
    }
    
    val useDynamicColor = settings?.useDynamicColor ?: true
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
            onSecondary = PatFlowDarkColors.onSecondary,
            secondaryContainer = PatFlowDarkColors.secondaryContainer,
            onSecondaryContainer = PatFlowDarkColors.onSecondaryContainer,
            tertiary = PatFlowDarkColors.tertiary,
            onTertiary = PatFlowDarkColors.onTertiary,
            tertiaryContainer = PatFlowDarkColors.tertiaryContainer,
            onTertiaryContainer = PatFlowDarkColors.onTertiaryContainer,
            error = PatFlowDarkColors.error,
            onError = PatFlowDarkColors.onError,
            errorContainer = PatFlowDarkColors.errorContainer,
            onErrorContainer = PatFlowDarkColors.onErrorContainer,
            background = PatFlowDarkColors.background,
            onBackground = PatFlowDarkColors.onBackground,
            surface = PatFlowDarkColors.surface,
            onSurface = PatFlowDarkColors.onSurface,
            surfaceVariant = PatFlowDarkColors.surfaceVariant,
            onSurfaceVariant = PatFlowDarkColors.onSurfaceVariant,
            outline = PatFlowDarkColors.outline,
            outlineVariant = PatFlowDarkColors.outlineVariant,
            surfaceContainerLowest = PatFlowDarkColors.surfaceContainerLowest,
            surfaceContainerLow = PatFlowDarkColors.surfaceContainerLow,
            surfaceContainer = PatFlowDarkColors.surfaceContainer,
            surfaceContainerHigh = PatFlowDarkColors.surfaceContainerHigh,
            surfaceContainerHighest = PatFlowDarkColors.surfaceContainerHighest,
        )
        else -> lightColorScheme(
            primary = PatFlowLightColors.primary,
            onPrimary = PatFlowLightColors.onPrimary,
            primaryContainer = PatFlowLightColors.primaryContainer,
            onPrimaryContainer = PatFlowLightColors.onPrimaryContainer,
            secondary = PatFlowLightColors.secondary,
            onSecondary = PatFlowLightColors.onSecondary,
            secondaryContainer = PatFlowLightColors.secondaryContainer,
            onSecondaryContainer = PatFlowLightColors.onSecondaryContainer,
            tertiary = PatFlowLightColors.tertiary,
            onTertiary = PatFlowLightColors.onTertiary,
            tertiaryContainer = PatFlowLightColors.tertiaryContainer,
            onTertiaryContainer = PatFlowLightColors.onTertiaryContainer,
            error = PatFlowLightColors.error,
            onError = PatFlowLightColors.onError,
            errorContainer = PatFlowLightColors.errorContainer,
            onErrorContainer = PatFlowLightColors.onErrorContainer,
            background = PatFlowLightColors.background,
            onBackground = PatFlowLightColors.onBackground,
            surface = PatFlowLightColors.surface,
            onSurface = PatFlowLightColors.onSurface,
            surfaceVariant = PatFlowLightColors.surfaceVariant,
            onSurfaceVariant = PatFlowLightColors.onSurfaceVariant,
            outline = PatFlowLightColors.outline,
            outlineVariant = PatFlowLightColors.outlineVariant,
            surfaceContainerLowest = PatFlowLightColors.surfaceContainerLowest,
            surfaceContainerLow = PatFlowLightColors.surfaceContainerLow,
            surfaceContainer = PatFlowLightColors.surfaceContainer,
            surfaceContainerHigh = PatFlowLightColors.surfaceContainerHigh,
            surfaceContainerHighest = PatFlowLightColors.surfaceContainerHighest,
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
