package com.patflow.app.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowTheme

/**
 * Design System §7.3 — Text Fields.
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    helperText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        isError = isError,
        supportingText = helperText?.let { { Text(it) } },
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        singleLine = singleLine,
        shape = PatFlowShapes.sm,
    )
}

/**
 * Specialized text field for currency/amount input.
 */
@Composable
fun AmountTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = "Amount",
    isError: Boolean = false,
    helperText: String? = null,
    currencySymbol: String = "₱"
) {
    AppTextField(
        value = value,
        onValueChange = { newValue ->
            // Basic validation to allow only numbers and decimal separator
            if (newValue.all { it.isDigit() || it == '.' || it == ',' }) {
                onValueChange(newValue)
            }
        },
        modifier = modifier,
        label = label,
        isError = isError,
        helperText = helperText,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        leadingIcon = {
            Text(
                text = currencySymbol,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    PatFlowTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppTextField(
                value = "",
                onValueChange = {},
                label = "Regular Field",
                placeholder = "Type something..."
            )
            AppTextField(
                value = "Error state",
                onValueChange = {},
                label = "Error Field",
                isError = true,
                helperText = "This field is required"
            )
            AmountTextField(
                value = "1,250.00",
                onValueChange = {}
            )
        }
    }
}
