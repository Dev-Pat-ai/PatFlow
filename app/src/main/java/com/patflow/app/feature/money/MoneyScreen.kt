package com.patflow.app.feature.money

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.patflow.app.core.components.AppSegmentedControl
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.components.SpeedDialAction
import com.patflow.app.core.components.SpeedDialFab
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.feature.bills.BillListScreen
import com.patflow.app.feature.budget.BudgetListScreen
import com.patflow.app.feature.income.IncomeListScreen
import com.patflow.app.feature.savings.SavingsGoalListScreen
import kotlinx.coroutines.launch

/**
 * Screen for managing financial entities: Bills, Income, and Savings (Architecture §6).
 * Updated in Phase 10 & 11 to include Budgets and Savings Goals.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneyScreen(
    onBillClick: (Long) -> Unit,
    onEditBillClick: (Long) -> Unit,
    onAddBillClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onManageIncomeSourcesClick: () -> Unit,
    onIncomeClick: (Long) -> Unit,
    onAddBudgetClick: () -> Unit,
    onBudgetClick: (Long) -> Unit,
    onAddSavingsGoalClick: () -> Unit,
    onSavingsGoalClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = remember { listOf("Bills", "Income", "Savings", "Budgets") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val speedDialActions = remember(onAddBillClick, onAddIncomeClick, onAddBudgetClick, onAddSavingsGoalClick) {
        listOf(
            SpeedDialAction(
                label = "Add Bill",
                icon = Icons.AutoMirrored.Rounded.ReceiptLong,
                onClick = onAddBillClick
            ),
            SpeedDialAction(
                label = "Log Payment",
                icon = Icons.Rounded.Payments,
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Payment logging coming soon")
                    }
                }
            ),
            SpeedDialAction(
                label = "Add Income",
                icon = Icons.AutoMirrored.Rounded.TrendingUp,
                onClick = onAddIncomeClick
            ),
            SpeedDialAction(
                label = "New Budget",
                icon = Icons.Rounded.Savings,
                onClick = onAddBudgetClick
            ),
            SpeedDialAction(
                label = "New Goal",
                icon = Icons.Rounded.Savings,
                onClick = onAddSavingsGoalClick
            )
        )
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                AppTopBar(
                    title = "Money",
                    actions = {
                        if (selectedTabIndex == 1) { // Income tab
                            IconButton(onClick = onManageIncomeSourcesClick) {
                                Icon(Icons.AutoMirrored.Rounded.List, contentDescription = "Manage Sources")
                            }
                        }
                        IconButton(onClick = { /* TODO: Search */ }) {
                            Icon(Icons.Rounded.Search, contentDescription = "Search")
                        }
                    }
                )
                AppSegmentedControl(
                    options = tabs,
                    selectedIndex = selectedTabIndex,
                    onOptionSelected = { selectedTabIndex = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = PatFlowSpacing.space4, vertical = PatFlowSpacing.space2)
                )
            }
        },
        floatingActionButton = {
            if (selectedTabIndex < 4) { // Show on all implemented tabs
                SpeedDialFab(actions = speedDialActions)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTabIndex) {
                0 -> BillListScreen(
                    onBillClick = onBillClick,
                    onEditClick = onEditBillClick
                )
                1 -> IncomeListScreen(
                    onAddIncomeClick = onAddIncomeClick,
                    onManageSourcesClick = onManageIncomeSourcesClick,
                    onEntryClick = onIncomeClick
                )
                2 -> SavingsGoalListScreen(
                    onAddGoalClick = onAddSavingsGoalClick,
                    onGoalClick = onSavingsGoalClick
                )
                3 -> BudgetListScreen(
                    onAddBudgetClick = onAddBudgetClick,
                    onBudgetClick = onBudgetClick
                )
            }
        }
    }
}
