package com.patflow.app.feature.budget

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
import com.patflow.app.core.components.TopBarType
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.domain.model.BudgetType
import kotlinx.datetime.LocalDate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBudgetScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditBudgetViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                AddEditBudgetViewModel.UiEvent.SaveSuccess -> onNavigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (uiState.isEditMode) "Edit Budget" else "New Budget",
                type = TopBarType.Small,
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
                .padding(PatFlowSpacing.space5),
            verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
        ) {
            AppTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                label = "Budget Name",
                placeholder = "e.g. Monthly Allowance"
            )

            AmountTextField(
                value = uiState.amount,
                onValueChange = viewModel::onAmountChange,
                label = "Total Amount"
            )

            BudgetTypeDropdown(
                selectedType = uiState.type,
                onTypeSelected = viewModel::onTypeChange
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)) {
                AppTextField(
                    value = uiState.startDate.toString(),
                    onValueChange = {},
                    label = "Start Date",
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { showStartDatePicker = true }) {
                            Icon(Icons.Rounded.CalendarToday, contentDescription = "Pick Date")
                        }
                    }
                )
                AppTextField(
                    value = uiState.endDate.toString(),
                    onValueChange = {},
                    label = "End Date",
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { showEndDatePicker = true }) {
                            Icon(Icons.Rounded.CalendarToday, contentDescription = "Pick Date")
                        }
                    }
                )
            }

            AppButton(
                onClick = viewModel::saveBudget,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.isEditMode) "Save Changes" else "Create Budget")
            }
        }
    }

    if (showStartDatePicker) {
        AppDatePickerDialog(
            onDateSelected = { date -> date?.let { viewModel.onStartDateChange(it) } },
            onDismiss = { showStartDatePicker = false },
            initialDate = uiState.startDate
        )
    }

    if (showEndDatePicker) {
        AppDatePickerDialog(
            onDateSelected = { date -> date?.let { viewModel.onEndDateChange(it) } },
            onDismiss = { showEndDatePicker = false },
            initialDate = uiState.endDate
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BudgetTypeDropdown(
    selectedType: BudgetType,
    onTypeSelected: (BudgetType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        AppTextField(
            value = selectedType.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            onValueChange = {},
            readOnly = true,
            label = "Budget Type",
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            BudgetType.entries.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
