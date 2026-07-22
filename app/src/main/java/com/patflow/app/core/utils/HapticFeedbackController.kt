package com.patflow.app.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

/**
 * Design System §12 — HapticFeedbackController.
 * Centralizes haptic feedback patterns for the application.
 */
class HapticFeedbackController(private val hapticFeedback: HapticFeedback) {

    /**
     * Confirms an action already visually completed (e.g., Save, Mark Paid).
     */
    fun confirm() {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    /**
     * Signals a failed action or rejection (e.g., PIN failure).
     */
    fun reject() {
        // Compose doesn't have a direct Reject equivalent in HapticFeedbackType yet,
        // so we simulate or wait for future API updates.
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    /**
     * Light tick for selection changes (e.g., multi-select toggle).
     */
    fun tick() {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }
}

@Composable
fun rememberHapticFeedbackController(): HapticFeedbackController {
    val hapticFeedback = LocalHapticFeedback.current
    return remember(hapticFeedback) {
        HapticFeedbackController(hapticFeedback)
    }
}
