package com.patflow.app.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.patflow.app.core.theme.PatFlowTheme

/**
 * Design System §7.5 — ConfirmationDialog.
 * Base M3 dialog for routine confirmations.
 */
@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: String,
    modifier: Modifier = Modifier,
    confirmLabel: String = "Confirm",
    dismissLabel: String = "Cancel"
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            AppButton(onClick = onConfirm, type = AppButtonType.Text) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            AppButton(onClick = onDismissRequest, type = AppButtonType.Text) {
                Text(dismissLabel)
            }
        },
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        },
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    )
}

/**
 * Design System §7.5 — DeleteConfirmationDialog.
 * Specialized dialog for destructive actions with prominent error styling.
 */
@Composable
fun DeleteConfirmationDialog(
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit,
    title: String,
    text: String,
    modifier: Modifier = Modifier,
    deleteLabel: String = "Delete"
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            AppButton(
                onClick = onDelete,
                type = AppButtonType.Text,
                modifier = Modifier
            ) {
                Text(text = deleteLabel, color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            AppButton(onClick = onDismissRequest, type = AppButtonType.Text) {
                Text("Cancel")
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        },
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    )
}

@Preview
@Composable
private fun AppDialogsPreview() {
    PatFlowTheme {
        Column {
            DeleteConfirmationDialog(
                onDismissRequest = {},
                onDelete = {},
                title = "Delete Bill?",
                text = "This will permanently remove the bill and all its payment history. This action cannot be undone."
            )
        }
    }
}
