package com.patflow.app.feature.income

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AmountTextField
import com.patflow.app.core.components.AppButton
import com.patflow.app.core.components.AppDatePickerDialog
import com.patflow.app.core.components.AppTextField
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.CategoryChip
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.rememberHapticFeedbackController
import com.patflow.app.domain.model.RecurrenceType
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditIncomeSourceScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditIncomeSourceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val haptic = rememberHapticFeedbackController()
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                AddEditIncomeSourceViewModel.UiEvent.SaveSuccess -> {
                    haptic.confirm()
                    onNavigateBack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (uiState.isEditMode) "Edit Template" else "New Recurring Income",
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
                label = "Template Name",
                placeholder = "e.g. Monthly Salary"
            )

            AmountTextField(
                value = uiState.amount,
                onValueChange = viewModel::onAmountChange,
                label = "Amount"
            )

            IncomeCategoryDropdownForSource(
                categories = categories,
                selectedCategory = uiState.category,
                onCategorySelected = viewModel::onCategoryChange
            )

            AppTextField(
                value = uiState.startDate.toString(),
                onValueChange = {},
                label = "Start Date",
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Rounded.CalendarToday, contentDescription = "Pick Date")
                    }
                }
            )

            RecurrenceTypeDropdown(
                selectedType = uiState.recurrenceType,
                onTypeSelected = viewModel::onRecurrenceTypeChange
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Is Active", style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
                Switch(checked = uiState.isActive, onCheckedChange = { viewModel.toggleActive() })
            }

            AppButton(
                onClick = viewModel::saveSource,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.isEditMode) "Save Changes" else "Create Template")
            }
        }
    }

    if (showDatePicker) {
        AppDatePickerDialog(
            onDateSelected = { date: LocalDate? -> date?.let { viewModel.onDateChange(it) } },
            onDismiss = { showDatePicker = false },
            initialDate = uiState.startDate
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IncomeCategoryDropdownForSource(
    categories: List<com.patflow.app.domain.model.IncomeCategory>,
    selectedCategory: com.patflow.app.domain.model.IncomeCategory?,
    onCategorySelected: (com.patflow.app.domain.model.IncomeCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        AppTextField(
            value = selectedCategory?.name ?: "Select Category",
            onValueChange = {},
            readOnly = true,
            label = "Category",
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            CategoryChip(category = CategoryMapper.mapToType(category.name))
                        }
                    },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecurrenceTypeDropdown(
    selectedType: RecurrenceType,
    onTypeSelected: (RecurrenceType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        AppTextField(
            value = selectedType.name.lowercase().replaceFirstChar { it.titlecase(java.util.Locale.getDefault()) },
            onValueChange = {},
            readOnly = true,
            label = "Recurrence",
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            RecurrenceType.entries.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name.lowercase().replaceFirstChar { it.titlecase(java.util.Locale.getDefault()) }) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
