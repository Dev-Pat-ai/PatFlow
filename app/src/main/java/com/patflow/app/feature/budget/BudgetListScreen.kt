package com.patflow.app.feature.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import com.patflow.app.core.theme.PatFlowSpacing

@Composable
fun BudgetListScreen(
    onAddBudgetClick: () -> Unit,
    onBudgetClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            BudgetUiState.Loading -> LoadingState()
            BudgetUiState.Empty -> EmptyState(
                title = "No budgets set",
                description = "Plan your spending by creating your first budget.",
                icon = Icons.Rounded.AccountBalanceWallet,
                action = {
                    AppButton(onClick = onAddBudgetClick) {
                        Text("Create Budget")
                    }
                }
            )
            is BudgetUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(PatFlowSpacing.space4),
                    verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
                ) {
                    items(state.budgets) { analytics ->
                        BudgetProgressCard(
                            name = analytics.budget.name,
                            totalAmount = analytics.budget.totalAmount,
                            amountUsed = analytics.amountUsed,
                            percentageUsed = analytics.percentageUsed,
                            currencyCode = analytics.budget.currencyCode,
                            onClick = { onBudgetClick(analytics.budget.id) }
                        )
                    }
                }
            }
            is BudgetUiState.Error -> FullScreenError(title = "Error", description = state.message)
        }
    }
}
