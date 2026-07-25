package com.patflow.app.feature.income

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.automirrored.rounded.Undo
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.theme.patFlowCategoryColors
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.core.utils.rememberHapticFeedbackController
import com.patflow.app.domain.model.IncomeWithDetails

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

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (selectedIds.isNotEmpty()) {
            ContextualIncomeActionBar(
                selectedCount = selectedIds.size,
                onClose = viewModel::clearSelection,
                onDelete = viewModel::deleteSelected
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
        ) {
            // Header Section
            item {
                IncomeListHeader(
                    searchQuery = searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    categories = categories,
                    selectedCategoryId = selectedCategoryId,
                    onCategoryChange = viewModel::onCategoryFilterChange
                )
            }

            when (val state = uiState) {
                IncomeUiState.Loading -> {
                    item { LoadingState() }
                }
                is IncomeUiState.Success -> {
                    if (state.entries.isEmpty()) {
                        item {
                            EmptyState(
                                title = "No income logged",
                                description = "Record your first income to start tracking your cash flow.",
                                icon = Icons.AutoMirrored.Rounded.ReceiptLong
                            )
                        }
                    } else {
                        items(state.entries, key = { it.entry.id }) { item ->
                            val isSelected = selectedIds.contains(item.entry.id)
                            SwipeableBillRow(
                                modifier = Modifier.padding(horizontal = PatFlowSpacing.space5),
                                onMarkAsPaid = { viewModel.duplicateEntry(item.entry.id) },
                                onEdit = { viewModel.deleteEntry(item.entry.id) }
                            ) {
                                IncomeItem(
                                    history = item,
                                    isSelected = isSelected,
                                    onClick = {
                                        if (selectedIds.isNotEmpty()) {
                                            haptic.tick()
                                            viewModel.toggleSelection(item.entry.id)
                                        } else {
                                            onEntryClick(item.entry.id)
                                        }
                                    },
                                    onLongClick = {
                                        haptic.confirm()
                                        viewModel.toggleSelection(item.entry.id)
                                    }
                                )
                            }
                        }
                    }
                }
                is IncomeUiState.Error -> {
                    item {
                        Text(text = "Error: ${state.message}", modifier = Modifier.padding(16.dp))
                    }
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
            .padding(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        SearchTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = "Search income...",
            leadingIcon = { Icon(Icons.Rounded.Search, null) },
            trailingIcon = { Icon(Icons.Rounded.Mic, null) }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
        ) {
            FilterChip(
                modifier = Modifier.weight(1f),
                selected = selectedCategoryId == null,
                onClick = { onCategoryChange(null) },
                label = { Text("All", modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center) }
            )
            
            categories.take(2).forEach { category ->
                FilterChip(
                    modifier = Modifier.weight(1f),
                    selected = selectedCategoryId == category.id,
                    onClick = { onCategoryChange(category.id) },
                    label = { 
                        Text(
                            text = category.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        ) 
                    }
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = PatFlowShapes.lg,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer 
                             else MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(PatFlowSpacing.space4)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
        ) {
            val categoryColors = patFlowCategoryColors()
            val categoryType = CategoryMapper.mapToType(history.entry.category.name)
            val colors = when (categoryType) {
                CategoryType.SALARY -> categoryColors.salary
                CategoryType.FREELANCE -> categoryColors.freelance
                CategoryType.BUSINESS -> categoryColors.business
                CategoryType.ALLOWANCE -> categoryColors.allowance
                CategoryType.BONUS -> categoryColors.bonus
                CategoryType.COMMISSION -> categoryColors.commission
                CategoryType.INVESTMENT -> categoryColors.investment
                CategoryType.CASHBACK -> categoryColors.cashback
                CategoryType.REFUND -> categoryColors.refund
                CategoryType.GIFT -> categoryColors.gift
                else -> categoryColors.other
            }
            
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = colors.containerColor, shape = PatFlowShapes.full),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when(categoryType) {
                        CategoryType.SALARY -> Icons.Rounded.Work
                        CategoryType.FREELANCE -> Icons.Rounded.LaptopMac
                        CategoryType.BUSINESS -> Icons.Rounded.Store
                        CategoryType.ALLOWANCE -> Icons.Rounded.ChildCare
                        CategoryType.BONUS -> Icons.Rounded.Celebration
                        CategoryType.COMMISSION -> Icons.AutoMirrored.Rounded.TrendingUp
                        CategoryType.INVESTMENT -> Icons.Rounded.AccountBalanceWallet
                        CategoryType.CASHBACK -> Icons.Rounded.Payments
                        CategoryType.REFUND -> Icons.AutoMirrored.Rounded.Undo
                        CategoryType.GIFT -> Icons.Rounded.CardGiftcard
                        else -> Icons.Rounded.AddCircle
                    },
                    contentDescription = null,
                    tint = colors.onColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = history.sourceName ?: history.entry.note ?: "Income",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = history.entry.category.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "+${CurrencyFormatter.formatAmount(history.entry.amount, history.entry.currencyCode)}",
                    style = MaterialTheme.typography.titleMedium.copy(fontFeatureSettings = "tnum"),
                    color = Color(0xFF10B981),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = history.entry.entryDate.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
                .padding(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2)
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
