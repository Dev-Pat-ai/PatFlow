package com.patflow.app.feature.income

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppFab
import com.patflow.app.core.components.AppTextField
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.CategoryChip
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.components.LoadingState
import com.patflow.app.core.components.SwipeableBillRow
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.core.utils.rememberHapticFeedbackController
import com.patflow.app.domain.model.IncomeWithDetails

/**
 * Screen for viewing a list of income entries (Architecture §6).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeListScreen(
    onAddIncomeClick: () -> Unit,
    onManageSourcesClick: () -> Unit,
    onEntryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategoryId by viewModel.selectedCategoryId.collectAsState()
    val selectedIds by viewModel.selectedIds.collectAsState()
    val haptic = rememberHapticFeedbackController()

    Scaffold(
        topBar = {
            if (selectedIds.isNotEmpty()) {
                ContextualIncomeActionBar(
                    selectedCount = selectedIds.size,
                    onClose = viewModel::clearSelection,
                    onDelete = viewModel::deleteSelected
                )
            } else {
                AppTopBar(
                    title = "Income",
                    actions = {
                        IconButton(onClick = onManageSourcesClick) {
                            Icon(Icons.AutoMirrored.Rounded.List, contentDescription = "Manage Sources")
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (selectedIds.isEmpty()) {
                AppFab(
                    onClick = onAddIncomeClick,
                    icon = Icons.Rounded.Add,
                    contentDescription = "Add Income"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (selectedIds.isEmpty()) {
                IncomeListHeader(
                    searchQuery = searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    categories = categories,
                    selectedCategoryId = selectedCategoryId,
                    onCategoryChange = viewModel::onCategoryFilterChange
                )
            }

            when (val state = uiState) {
                IncomeUiState.Loading -> LoadingState()
                is IncomeUiState.Success -> {
                    if (state.entries.isEmpty()) {
                        EmptyState(
                            title = "No income logged",
                            description = "Record your first income to start tracking your cash flow.",
                            icon = Icons.AutoMirrored.Rounded.ReceiptLong
                        )
                    } else {
                        IncomeListContent(
                            entries = state.entries,
                            selectedIds = selectedIds,
                            onEntryClick = { id ->
                                if (selectedIds.isNotEmpty()) {
                                    haptic.tick()
                                    viewModel.toggleSelection(id)
                                } else {
                                    onEntryClick(id)
                                }
                            },
                            onLongClick = { id ->
                                haptic.confirm()
                                viewModel.toggleSelection(id)
                            },
                            onDelete = viewModel::deleteEntry,
                            onDuplicate = viewModel::duplicateEntry
                        )
                    }
                }
                is IncomeUiState.Error -> {
                    Text(text = "Error: ${state.message}", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
private fun IncomeListHeader(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    categories: List<com.patflow.app.domain.model.IncomeCategory>,
    selectedCategoryId: Long?,
    onCategoryChange: (Long?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PatFlowSpacing.space4, vertical = PatFlowSpacing.space2),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
    ) {
        AppTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = "Search income..."
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = selectedCategoryId == category.id,
                    onClick = { onCategoryChange(if (selectedCategoryId == category.id) null else category.id) },
                    label = { Text(category.name) }
                )
            }
        }
    }
}

@Composable
private fun ContextualIncomeActionBar(
    selectedCount: Int,
    onClose: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = PatFlowSpacing.space4, vertical = PatFlowSpacing.space2)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Close")
            }
            Text(
                text = "$selectedCount selected",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onDelete) {
                Icon(Icons.Rounded.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
private fun IncomeListContent(
    entries: List<IncomeWithDetails>,
    selectedIds: Set<Long>,
    onEntryClick: (Long) -> Unit,
    onLongClick: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    onDuplicate: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        items(entries, key = { it.entry.id }) { item ->
            val isSelected = selectedIds.contains(item.entry.id)
            SwipeableBillRow(
                onMarkAsPaid = { onDuplicate(item.entry.id) }, // Reusing swipe right for Duplicate
                onEdit = { onDelete(item.entry.id) } // Reusing swipe left for Delete
            ) {
                IncomeItem(
                    history = item,
                    isSelected = isSelected,
                    onClick = { onEntryClick(item.entry.id) },
                    onLongClick = { onLongClick(item.entry.id) }
                )
            }
        }
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun IncomeItem(
    history: IncomeWithDetails,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = com.patflow.app.core.theme.PatFlowShapes.lg,
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(PatFlowSpacing.space4)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
        ) {
            CategoryChip(category = CategoryMapper.mapToType(history.entry.category.name))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = history.sourceName ?: history.entry.note ?: "Income",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = history.entry.entryDate.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = CurrencyFormatter.formatAmount(history.entry.amount, history.entry.currencyCode),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
