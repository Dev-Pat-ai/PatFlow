package com.patflow.app.feature.money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.patflow.app.feature.income.IncomeListScreen
import kotlinx.coroutines.launch

/**
 * Screen for managing financial entities: Bills, Income, and Savings (Architecture §6).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneyScreen(
    onBillClick: (Long) -> Unit,
    onEditBillClick: (Long) -> Unit,
    onAddBillClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onIncomeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = remember { listOf("Bills", "Income", "Savings") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val speedDialActions = remember(onAddBillClick) {
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
                label = "Add Savings",
                icon = Icons.Rounded.Savings,
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Savings goals coming soon")
                    }
                }
            )
        )
    }

    Scaffold(
        topBar = {
            Column {
                AppTopBar(title = "Money")
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
            if (selectedTabIndex == 0) {
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
                    onEntryClick = onIncomeClick
                )
                2 -> ComingSoonState(title = "Savings goals are coming soon")
            }
        }
    }
}

@Composable
private fun ComingSoonState(title: String) {
    EmptyState(
        title = title,
        description = "This feature will be available in a later phase.",
        icon = Icons.Rounded.Payments
    )
}
