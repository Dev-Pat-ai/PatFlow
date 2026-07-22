package com.patflow.app.feature.bills

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
import com.patflow.app.core.components.CategoryType
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.domain.model.RecurrenceType
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBillScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEditBillViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories by viewModel.categories.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                AddEditBillViewModel.UiEvent.SaveSuccess -> onNavigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (uiState.isEditMode) "Edit Bill" else "Add Bill",
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
                label = "Bill Name",
                placeholder = "e.g. Meralco"
            )

            AmountTextField(
                value = uiState.amount,
                onValueChange = viewModel::onAmountChange,
                label = "Amount"
            )

            CategoryDropdown(
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

            RecurrenceDropdown(
                selectedType = uiState.recurrenceType,
                onTypeSelected = viewModel::onRecurrenceTypeChange
            )

            AppTextField(
                value = uiState.notes,
                onValueChange = viewModel::onNotesChange,
                label = "Notes (Optional)",
                singleLine = false,
                modifier = Modifier.height(100.dp)
            )

            AppButton(
                onClick = viewModel::saveBill,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.isEditMode) "Save Changes" else "Add Bill")
            }
        }
    }

    if (showDatePicker) {
        AppDatePickerDialog(
            onDateSelected = viewModel::onDateChange,
            onDismiss = { showDatePicker = false },
            initialDate = uiState.startDate
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryDropdown(
    categories: List<com.patflow.app.domain.model.Category>,
    selectedCategory: com.patflow.app.domain.model.Category?,
    onCategorySelected: (com.patflow.app.domain.model.Category) -> Unit
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
                            CategoryChip(category = mapCategoryToType(category.name))
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
private fun RecurrenceDropdown(
    selectedType: RecurrenceType,
    onTypeSelected: (RecurrenceType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        AppTextField(
            value = selectedType.name.lowercase().replaceFirstChar { it.titlecase() },
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
                    text = { Text(type.name.lowercase().replaceFirstChar { it.titlecase() }) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

private fun mapCategoryToType(name: String): CategoryType {
    return try {
        CategoryType.valueOf(name.uppercase().replace(" ", "_"))
    } catch (e: Exception) {
        CategoryType.ELECTRICITY
    }
}
