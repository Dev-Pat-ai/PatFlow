package com.patflow.app.feature.money

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patflow.app.core.components.*
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.feature.bills.BillListScreen
import com.patflow.app.feature.budget.BudgetListScreen
import com.patflow.app.feature.income.IncomeListScreen
import com.patflow.app.feature.savings.SavingsGoalListScreen
import kotlinx.coroutines.launch

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
    
    var showAddBillSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                AppTopBar(
                    title = "Money",
                    type = TopBarType.Small,
                    actions = {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Rounded.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Rounded.MoreVert, contentDescription = "More")
                        }
                    }
                )
                
                // Pill-styled Tab Row
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    edgePadding = 16.dp,
                    containerColor = Color.Transparent,
                    divider = {},
                    indicator = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTabIndex == index
                        Tab(
                            selected = isSelected,
                            onClick = { selectedTabIndex = index },
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 4.dp)
                                .height(40.dp),
                            text = {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer 
                                                    else Color.Transparent,
                                            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                                        )
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = when(index) {
                                                    0 -> Icons.AutoMirrored.Rounded.ReceiptLong
                                                    1 -> Icons.Rounded.AccountBalanceWallet
                                                    else -> Icons.Rounded.Savings
                                                },
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp),
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                        Text(
                                            text = title,
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer 
                                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    when (selectedTabIndex) {
                        0 -> showAddBillSheet = true
                        1 -> onAddIncomeClick()
                        2 -> onAddSavingsGoalClick()
                        3 -> onAddBudgetClick()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = com.patflow.app.core.theme.PatFlowShapes.xl
            ) {
                Icon(Icons.Rounded.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when (selectedTabIndex) {
                        0 -> "Add Bill"
                        1 -> "Add Income"
                        2 -> "Add Goal"
                        3 -> "Add Budget"
                        else -> "Add"
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTabIndex) {
                0 -> BillListScreen(
                    showAddSheet = showAddBillSheet,
                    onAddSheetDismiss = { showAddBillSheet = false },
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
