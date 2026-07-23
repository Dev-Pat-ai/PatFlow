package com.patflow.app.feature.bills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import com.patflow.app.core.components.AppSnackbarHost
import com.patflow.app.core.components.AppTextField
import com.patflow.app.core.components.BillCard
import com.patflow.app.core.components.CategoryType
import com.patflow.app.core.components.DeleteConfirmationDialog
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.components.FullScreenError
import com.patflow.app.core.components.SkeletonBox
import com.patflow.app.core.components.SwipeableBillRow
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.rememberHapticFeedbackController
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import java.util.Locale

/**
 * Screen for viewing a list of bills (Architecture §6).
 */
@Composable
fun BillListScreen(
    onBillClick: (Long) -> Unit,
    onEditClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BillListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedStatus by viewModel.selectedStatus.collectAsState()
    val selectedIds by viewModel.selectedIds.collectAsState()
    val haptic = rememberHapticFeedbackController()
    val snackbarHostState = remember { SnackbarHostState() }

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is BillListViewModel.UiEvent.ActionSuccess -> {
                    haptic.confirm()
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Contextual Top Bar or Search/Filters
            if (selectedIds.isNotEmpty()) {
                ContextualActionBar(
                    selectedCount = selectedIds.size,
                    onClose = viewModel::clearSelection,
                    onDelete = { showDeleteDialog = true },
                    onMarkAsPaid = { 
                        viewModel.markSelectedAsPaid()
                    }
                )
            } else {
                BillListHeader(
                    searchQuery = searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    selectedStatus = selectedStatus,
                    onStatusFilterChange = viewModel::onStatusFilterChange,
                    onSortToggle = viewModel::toggleSortOrder
                )
            }

            when (val state = uiState) {
                BillListUiState.Loading -> {
                    BillListLoading()
                }
                is BillListUiState.Success -> {
                    if (state.bills.isEmpty()) {
                        EmptyState(
                            title = "No bills found",
                            description = if (searchQuery.isNotEmpty()) "Try a different search term." else "Add your first bill to get started.",
                            icon = Icons.AutoMirrored.Rounded.ReceiptLong
                        )
                    } else {
                        BillListContent(
                            bills = state.bills,
                            selectedIds = selectedIds,
                            onBillClick = { id ->
                                if (selectedIds.isNotEmpty()) {
                                    haptic.tick()
                                    viewModel.toggleSelection(id)
                                } else {
                                    onBillClick(id)
                                }
                            },
                            onLongClick = { id ->
                                haptic.confirm()
                                viewModel.toggleSelection(id)
                            },
                            onMarkAsPaid = viewModel::markAsPaid,
                            onEdit = onEditClick
                        )
                    }
                }
                is BillListUiState.Error -> {
                    FullScreenError(
                        title = "Error loading bills",
                        description = state.message
                    )
                }
            }
        }

        AppSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { showDeleteDialog = false },
            onDelete = {
                showDeleteDialog = false
                viewModel.deleteSelectedBills()
            },
            title = "Delete Bills?",
            text = "This will permanently remove ${selectedIds.size} selected bills and their history."
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BillListHeader(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedStatus: BillStatus?,
    onStatusFilterChange: (BillStatus?) -> Unit,
    onSortToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PatFlowSpacing.space4, vertical = PatFlowSpacing.space2),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
        ) {
            AppTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = "Search bills...",
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onSortToggle) {
                Icon(Icons.AutoMirrored.Rounded.Sort, contentDescription = "Sort")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
        ) {
            BillStatus.entries.forEach { status ->
                FilterChip(
                    selected = selectedStatus == status,
                    onClick = { 
                        onStatusFilterChange(if (selectedStatus == status) null else status)
                    },
                    label = { 
                        Text(status.name.lowercase().replaceFirstChar { 
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() 
                        }) 
                    }
                )
            }
        }
    }
}

@Composable
private fun ContextualActionBar(
    selectedCount: Int,
    onClose: () -> Unit,
    onDelete: () -> Unit,
    onMarkAsPaid: () -> Unit
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
                Icon(Icons.Rounded.Close, contentDescription = "Close")
            }
            Text(
                text = "$selectedCount selected",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onMarkAsPaid) {
                Icon(Icons.Rounded.Check, contentDescription = "Mark as Paid")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Rounded.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
private fun BillListContent(
    bills: List<BillWithCycle>,
    selectedIds: Set<Long>,
    onBillClick: (Long) -> Unit,
    onLongClick: (Long) -> Unit,
    onMarkAsPaid: (Long) -> Unit,
    onEdit: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        items(bills, key = { it.bill.id }) { item ->
            val isSelected = selectedIds.contains(item.bill.id)
            SwipeableBillRow(
                onMarkAsPaid = { onMarkAsPaid(item.bill.id) },
                onEdit = { onEdit(item.bill.id) }
            ) {
                BillCard(
                    name = item.bill.name,
                    amount = item.currentCycle?.amountDue ?: item.bill.defaultAmount,
                    dueDate = item.currentCycle?.dueDate?.toString() ?: "N/A",
                    category = CategoryMapper.mapToType(item.bill.category.name),
                    status = item.currentCycle?.status ?: BillStatus.UNPAID,
                    isSelected = isSelected,
                    showSelection = selectedIds.isNotEmpty(),
                    currencyCode = item.bill.currencyCode,
                    onClick = { onBillClick(item.bill.id) },
                    onLongClick = { onLongClick(item.bill.id) }
                )
            }
        }
    }
}

@Composable
private fun BillListLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        repeat(5) {
            SkeletonBox(height = 80.dp)
        }
    }
}
