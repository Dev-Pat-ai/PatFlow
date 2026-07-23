package com.patflow.app.feature.savings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import com.patflow.app.core.theme.PatFlowSpacing
import kotlinx.datetime.LocalDate

import com.patflow.app.core.utils.rememberHapticFeedbackController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSavingsGoalScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditSavingsGoalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val haptic = rememberHapticFeedbackController()
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                AddEditSavingsGoalViewModel.UiEvent.SaveSuccess -> {
                    haptic.confirm()
                    onNavigateBack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (uiState.isEditMode) "Edit Goal" else "New Goal",
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(PatFlowSpacing.space4),
            verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
        ) {
            AppTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                label = "Goal Name",
                placeholder = "e.g. New Laptop"
            )

            AmountTextField(
                value = uiState.targetAmount,
                onValueChange = viewModel::onAmountChange,
                label = "Target Amount"
            )

            AppTextField(
                value = uiState.targetDate?.toString() ?: "No Deadline",
                onValueChange = {},
                label = "Target Date (Optional)",
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Rounded.CalendarToday, contentDescription = "Pick Date")
                    }
                }
            )

            AppTextField(
                value = uiState.notes,
                onValueChange = viewModel::onNotesChange,
                label = "Notes (Optional)",
                singleLine = false,
                modifier = Modifier.height(100.dp)
            )

            AppButton(
                onClick = viewModel::saveGoal,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.isEditMode) "Save Changes" else "Create Goal")
            }
        }
    }

    if (showDatePicker) {
        AppDatePickerDialog(
            onDateSelected = { date -> viewModel.onDateChange(date) },
            onDismiss = { showDatePicker = false },
            initialDate = uiState.targetDate
        )
    }
}
