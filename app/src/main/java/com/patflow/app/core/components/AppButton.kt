package com.patflow.app.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowTheme

/**
 * Design System §7.1 — Buttons.
 */
enum class AppButtonType {
    Filled, Tonal, Outlined, Text
}

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: AppButtonType = AppButtonType.Filled,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val buttonModifier = modifier.height(40.dp)
    val shape = PatFlowShapes.full

    when (type) {
        AppButtonType.Filled -> {
            Button(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = enabled,
                shape = shape,
                content = content
            )
        }
        AppButtonType.Tonal -> {
            FilledTonalButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = enabled,
                shape = shape,
                content = content
            )
        }
        AppButtonType.Outlined -> {
            OutlinedButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = enabled,
                shape = shape,
                content = content
            )
        }
        AppButtonType.Text -> {
            TextButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = enabled,
                shape = shape,
                content = content
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    PatFlowTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppButton(onClick = {}, type = AppButtonType.Filled) {
                Text("Filled Button")
            }
            AppButton(onClick = {}, type = AppButtonType.Tonal) {
                Text("Tonal Button")
            }
            AppButton(onClick = {}, type = AppButtonType.Outlined) {
                Text("Outlined Button")
            }
            AppButton(onClick = {}, type = AppButtonType.Text) {
                Text("Text Button")
            }
        }
    }
}
