package com.patflow.app.feature.bills

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
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppTextField
import com.patflow.app.core.components.BillCard
import com.patflow.app.core.components.CategoryType
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.components.FullScreenError
import com.patflow.app.core.components.SkeletonBox
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import java.util.Locale

@Composable
fun BillListScreen(
    onBillClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BillListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedStatus by viewModel.selectedStatus.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        // Search and Filters
        BillListHeader(
            searchQuery = searchQuery,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            selectedStatus = selectedStatus,
            onStatusFilterChange = viewModel::onStatusFilterChange,
            onSortToggle = viewModel::toggleSortOrder
        )

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
                        onBillClick = onBillClick
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
private fun BillListContent(
    bills: List<BillWithCycle>,
    onBillClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        items(bills, key = { it.bill.id }) { item ->
            BillCard(
                name = item.bill.name,
                amount = item.currentCycle?.amountDue ?: item.bill.defaultAmount,
                dueDate = item.currentCycle?.dueDate?.toString() ?: "N/A",
                category = mapCategoryToType(item.bill.category.name),
                status = item.currentCycle?.status ?: BillStatus.UNPAID,
                onClick = { onBillClick(item.bill.id) }
            )
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

private fun mapCategoryToType(name: String): CategoryType {
    return try {
        CategoryType.valueOf(name.uppercase().replace(" ", "_"))
    } catch (e: Exception) {
        CategoryType.ELECTRICITY
    }
}
