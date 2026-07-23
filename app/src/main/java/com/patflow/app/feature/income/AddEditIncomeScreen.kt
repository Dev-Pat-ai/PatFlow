package com.patflow.app.feature.income

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.datetime.LocalDate

/**
 * Screen for adding or editing an income entry.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditIncomeScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditIncomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val sources by viewModel.sources.collectAsState()
    val haptic = rememberHapticFeedbackController()
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                AddEditIncomeViewModel.UiEvent.SaveSuccess -> {
                    haptic.confirm()
                    onNavigateBack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (uiState.isEditMode) "Edit Income" else "Add Income",
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
            IncomeSourceDropdown(
                sources = sources,
                selectedSourceId = uiState.incomeSourceId,
                onSourceSelected = viewModel::onSourceChange
            )

            AmountTextField(
                value = uiState.amount,
                onValueChange = viewModel::onAmountChange,
                label = "Amount",
                isError = uiState.amountError != null,
                helperText = uiState.amountError,
                currencySymbol = com.patflow.app.core.utils.CurrencyFormatter.getSymbol(uiState.currencyCode)
            )

            IncomeCategoryDropdown(
                categories = categories,
                selectedCategory = uiState.category,
                onCategorySelected = viewModel::onCategoryChange,
                isError = uiState.categoryError != null,
                helperText = uiState.categoryError
            )

            AppTextField(
                value = uiState.date.toString(),
                onValueChange = {},
                label = "Date",
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Rounded.CalendarToday, contentDescription = "Pick Date")
                    }
                }
            )

            AppTextField(
                value = uiState.note,
                onValueChange = viewModel::onNoteChange,
                label = "Notes (Optional)",
                placeholder = "e.g. Salary Bonus",
                singleLine = false,
                modifier = Modifier.height(100.dp)
            )

            AppButton(
                onClick = viewModel::saveEntry,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.isEditMode) "Save Changes" else "Add Income")
            }
        }
    }

    if (showDatePicker) {
        AppDatePickerDialog(
            onDateSelected = { date: LocalDate? -> viewModel.onDateChange(date) },
            onDismiss = { showDatePicker = false },
            initialDate = uiState.date
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IncomeSourceDropdown(
    sources: List<com.patflow.app.domain.model.IncomeSource>,
    selectedSourceId: Long?,
    onSourceSelected: (com.patflow.app.domain.model.IncomeSource?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedSource = sources.find { it.id == selectedSourceId }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        AppTextField(
            value = selectedSource?.name ?: "One-time Income",
            onValueChange = {},
            readOnly = true,
            label = "Income Source",
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("One-time Income") },
                onClick = {
                    onSourceSelected(null)
                    expanded = false
                }
            )
            sources.forEach { source ->
                DropdownMenuItem(
                    text = { Text(source.name) },
                    onClick = {
                        onSourceSelected(source)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IncomeCategoryDropdown(
    categories: List<com.patflow.app.domain.model.IncomeCategory>,
    selectedCategory: com.patflow.app.domain.model.IncomeCategory?,
    onCategorySelected: (com.patflow.app.domain.model.IncomeCategory) -> Unit,
    isError: Boolean = false,
    helperText: String? = null
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
            isError = isError,
            helperText = helperText,
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
