package com.patflow.app.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing

/**
 * Design System §8.4 — AppSnackbarHost.
 * Custom snackbar styling for routine and meaningful success/error states.
 */
@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier.padding(PatFlowSpacing.space4)
    ) { data ->
        Snackbar(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            shape = PatFlowShapes.md,
            action = data.visuals.actionLabel?.let { actionLabel ->
                {
                    AppButton(
                        onClick = { data.performAction() },
                        type = AppButtonType.Text
                    ) {
                        Text(text = actionLabel, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        ) {
            Text(text = data.visuals.message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
