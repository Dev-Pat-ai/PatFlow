package com.patflow.app.feature.bills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.automirrored.rounded.ViewList
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.rememberHapticFeedbackController
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.feature.bills.components.AddBillBottomSheet
import com.patflow.app.feature.bills.components.BillDetailBottomSheet
import com.patflow.app.feature.bills.components.BillsOverviewCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillListScreen(
    showAddSheet: Boolean,
    onAddSheetDismiss: () -> Unit,
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
    var selectedBillForDetail by remember { mutableStateOf<BillWithCycle?>(null) }

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
            if (selectedIds.isNotEmpty()) {
                ContextualActionBar(
                    selectedCount = selectedIds.size,
                    onClose = viewModel::clearSelection,
                    onDelete = { showDeleteDialog = true },
                    onMarkAsPaid = { viewModel.markSelectedAsPaid() }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
            ) {
                // 1. Overview Card
                item {
                    val allBills = (uiState as? BillListUiState.Success)?.bills ?: emptyList()
                    val totalCount = allBills.size
                    val paidCount = allBills.count { it.currentCycle?.status == BillStatus.PAID }
                    val remainingCount = totalCount - paidCount
                    val paidAmount = allBills.sumOf { it.currentCycle?.amountPaid ?: 0.0 }
                    val remainingAmount = allBills.sumOf { (it.currentCycle?.amountDue ?: it.bill.defaultAmount) - (it.currentCycle?.amountPaid ?: 0.0) }
                    val progress = if (totalCount > 0) paidCount.toFloat() / totalCount else 0f

                    BillsOverviewCard(
                        remainingAmount = remainingAmount,
                        remainingCount = remainingCount,
                        paidAmount = paidAmount,
                        paidCount = paidCount,
                        totalCount = totalCount,
                        progress = progress,
                        currencyCode = "PHP",
                        modifier = Modifier.padding(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2)
                    )
                }

                // 2. Search and Filters
                item {
                    BillListHeader(
                        searchQuery = searchQuery,
                        onSearchQueryChange = viewModel::onSearchQueryChange,
                        selectedStatus = selectedStatus,
                        onStatusFilterChange = viewModel::onStatusFilterChange
                    )
                }

                when (val state = uiState) {
                    BillListUiState.Loading -> {
                        item { BillListLoading() }
                    }
                    is BillListUiState.Success -> {
                        val bills = state.bills
                        
                        // 3. Due Soon Section
                        val dueSoon = bills.filter { it.currentCycle?.status == BillStatus.OVERDUE || it.currentCycle?.status == BillStatus.UNPAID }.take(3)
                        if (dueSoon.isNotEmpty()) {
                            item {
                                SectionHeaderWithAction(
                                    title = "Due Soon (${dueSoon.size})",
                                    actionLabel = "View all",
                                    onActionClick = { /* TODO */ }
                                )
                            }
                            items(dueSoon) { item ->
                                BillCardItem(
                                    item = item,
                                    isSelected = selectedIds.contains(item.bill.id),
                                    showSelection = selectedIds.isNotEmpty(),
                                    onBillClick = { selectedBillForDetail = item },
                                    onLongClick = { 
                                        haptic.confirm()
                                        viewModel.toggleSelection(item.bill.id)
                                    },
                                    onMarkAsPaid = viewModel::markAsPaid,
                                    onEdit = onEditClick
                                )
                            }
                        }

                        // 4. All Bills Section
                        item {
                            SectionHeaderWithAction(
                                title = "All Bills",
                                actionLabel = "Sort: Due Date",
                                onActionClick = { viewModel.toggleSortOrder() }
                            )
                        }
                        
                        if (bills.isEmpty()) {
                            item {
                                EmptyState(
                                    title = "No bills found",
                                    description = if (searchQuery.isNotEmpty()) "Try a different search term." else "Add your first bill to get started.",
                                    icon = Icons.AutoMirrored.Rounded.ReceiptLong
                                )
                            }
                        } else {
                            items(bills) { item ->
                                BillCardItem(
                                    item = item,
                                    isSelected = selectedIds.contains(item.bill.id),
                                    showSelection = selectedIds.isNotEmpty(),
                                    onBillClick = { selectedBillForDetail = item },
                                    onLongClick = { 
                                        haptic.confirm()
                                        viewModel.toggleSelection(item.bill.id)
                                    },
                                    onMarkAsPaid = viewModel::markAsPaid,
                                    onEdit = onEditClick
                                )
                            }
                        }
                    }
                    is BillListUiState.Error -> {
                        item {
                            FullScreenError(title = "Error loading bills", description = state.message)
                        }
                    }
                }
            }
        }
    }

    // Bottom Sheets & Dialogs
    if (showAddSheet) {
        AddBillBottomSheet(
            onDismiss = onAddSheetDismiss,
            onSave = { _, _, _, _, _ -> onAddSheetDismiss() }
        )
    }

    selectedBillForDetail?.let { item ->
        BillDetailBottomSheet(
            bill = item.bill,
            status = item.currentCycle?.status ?: BillStatus.UNPAID,
            amountDue = item.currentCycle?.amountDue ?: item.bill.defaultAmount,
            dueDate = item.currentCycle?.dueDate?.toString() ?: "N/A",
            onDismiss = { selectedBillForDetail = null },
            onEdit = { 
                selectedBillForDetail = null
                onEditClick(item.bill.id) 
            },
            onMarkAsPaid = {
                selectedBillForDetail = null
                viewModel.markAsPaid(item.bill.id)
            }
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

@Composable
private fun BillCardItem(
    item: BillWithCycle,
    isSelected: Boolean,
    showSelection: Boolean,
    onBillClick: () -> Unit,
    onLongClick: () -> Unit,
    onMarkAsPaid: (Long) -> Unit,
    onEdit: (Long) -> Unit
) {
    SwipeableBillRow(
        modifier = Modifier.padding(horizontal = PatFlowSpacing.space5),
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
            showSelection = showSelection,
            currencyCode = item.bill.currencyCode,
            onClick = onBillClick,
            onLongClick = onLongClick
        )
    }
}

@Composable
private fun SectionHeaderWithAction(
    title: String,
    actionLabel: String,
    onActionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = actionLabel,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onActionClick() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BillListHeader(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedStatus: BillStatus?,
    onStatusFilterChange: (BillStatus?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
        ) {
            SearchTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = "Search bills...",
                modifier = Modifier.weight(1f),
                leadingIcon = { Icon(Icons.Rounded.Search, null) },
                trailingIcon = { Icon(Icons.Rounded.Mic, null) }
            )
            IconButton(
                onClick = { /* TODO: Filters */ },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Rounded.FilterList, contentDescription = "Filter")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
        ) {
            val allStatus = listOf(null, BillStatus.OVERDUE, BillStatus.UNPAID, BillStatus.PAID)
            allStatus.forEach { status ->
                val actualLabel = when(status) {
                    null -> "All"
                    BillStatus.OVERDUE -> "Overdue"
                    BillStatus.UNPAID -> "Due Soon"
                    BillStatus.PAID -> "Paid"
                }

                FilterChip(
                    modifier = Modifier.weight(1f),
                    selected = selectedStatus == status,
                    onClick = { onStatusFilterChange(status) },
                    label = { 
                        Text(
                            text = actualLabel,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            style = MaterialTheme.typography.labelMedium
                        ) 
                    },
                    leadingIcon = if (selectedStatus == status) {
                        { Icon(Icons.Rounded.Check, null, modifier = Modifier.size(16.dp)) }
                    } else if (status != null) {
                        { 
                            Icon(
                                imageVector = when(status) {
                                    BillStatus.OVERDUE -> Icons.Rounded.ErrorOutline
                                    BillStatus.UNPAID -> Icons.Rounded.Schedule
                                    BillStatus.PAID -> Icons.Rounded.CheckCircleOutline
                                },
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else {
                        { Icon(Icons.AutoMirrored.Rounded.ViewList, null, modifier = Modifier.size(16.dp)) }
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
                .padding(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2)
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
private fun BillListLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PatFlowSpacing.space5),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        repeat(5) {
            SkeletonBox(height = 80.dp)
        }
    }
}
