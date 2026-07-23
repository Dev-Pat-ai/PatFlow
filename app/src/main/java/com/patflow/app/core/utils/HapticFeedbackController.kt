package com.patflow.app.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HapticViewModel @Inject constructor(
    getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {
    val hapticEnabled = getUserSettingsUseCase()
        .map { it.hapticFeedbackEnabled }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )
}

/**
 * Design System §12 — HapticFeedbackController.
 * Centralizes haptic feedback patterns for the application.
 * Respects user preferences for haptic feedback.
 */
class HapticFeedbackController(
    private val hapticFeedback: HapticFeedback,
    private val enabled: Boolean
) {

    /**
     * Confirms an action already visually completed (e.g., Save, Mark Paid).
     */
    fun confirm() {
        if (enabled) hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    /**
     * Signals a failed action or rejection (e.g., PIN failure).
     */
    fun reject() {
        if (enabled) hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    /**
     * Light tick for selection changes (e.g., multi-select toggle).
     */
    fun tick() {
        if (enabled) hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }
}

@Composable
fun rememberHapticFeedbackController(
    viewModel: HapticViewModel = hiltViewModel()
): HapticFeedbackController {
    val hapticFeedback = LocalHapticFeedback.current
    val enabled by viewModel.hapticEnabled.collectAsState()
    
    return remember(hapticFeedback, enabled) {
        HapticFeedbackController(hapticFeedback, enabled)
    }
}
