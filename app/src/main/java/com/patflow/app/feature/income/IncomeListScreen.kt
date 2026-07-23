package com.patflow.app.feature.income

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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.patflow.app.core.components.SectionHeader
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.IncomeWithDetails

/**
 * Screen for viewing a list of income entries (Architecture §6).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeListScreen(
    onAddIncomeClick: () -> Unit,
    onEntryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategoryId by viewModel.selectedCategoryId.collectAsState()

    Scaffold(
        topBar = { AppTopBar(title = "Income") },
        floatingActionButton = {
            AppFab(
                onClick = onAddIncomeClick,
                icon = Icons.Rounded.Add,
                contentDescription = "Add Income"
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            IncomeListHeader(
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                categories = categories,
                selectedCategoryId = selectedCategoryId,
                onCategoryChange = viewModel::onCategoryFilterChange
            )

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
                            onEntryClick = onEntryClick
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
private fun IncomeListContent(
    entries: List<IncomeWithDetails>,
    onEntryClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        items(entries, key = { it.entry.id }) { item ->
            IncomeItem(
                history = item,
                onClick = { onEntryClick(item.entry.id) }
            )
        }
    }
}

@Composable
private fun IncomeItem(
    history: IncomeWithDetails,
    onClick: () -> Unit
) {
    androidx.compose.material3.Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = com.patflow.app.core.theme.PatFlowShapes.lg,
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
