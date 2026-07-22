package com.patflow.app.core.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

/**
 * Design System — DatePicker wrapper.
 * Provides a consistent M3 DatePicker dialog.
 * Uses kotlinx.datetime.LocalDate for domain consistency.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    onDateSelected: (LocalDate?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialDate: LocalDate? = null,
    selectableDates: SelectableDates = object : SelectableDates {}
) {
    val initialMillis = initialDate?.atStartOfDayIn(TimeZone.UTC)?.toEpochMilliseconds()
    
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis,
        selectableDates = selectableDates
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            AppButton(
                onClick = {
                    val selectedDate = datePickerState.selectedDateMillis?.let {
                        Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.UTC).date
                    }
                    onDateSelected(selectedDate)
                    onDismiss()
                },
                type = AppButtonType.Text
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            AppButton(onClick = onDismiss, type = AppButtonType.Text) {
                Text("Cancel")
            }
        },
        modifier = modifier
    ) {
        DatePicker(state = datePickerState)
    }
}
