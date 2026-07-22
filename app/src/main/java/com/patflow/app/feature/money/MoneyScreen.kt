package com.patflow.app.feature.money

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.patflow.app.core.components.AppFab
import com.patflow.app.core.components.AppSegmentedControl
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.feature.bills.BillListScreen
import androidx.compose.material.icons.rounded.Payments

@Composable
fun MoneyScreen(
    onBillClick: (Long) -> Unit,
    onAddBillClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Bills", "Income", "Savings")

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
                AppFab(
                    onClick = onAddBillClick,
                    icon = Icons.Rounded.Add,
                    contentDescription = "Add Bill"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTabIndex) {
                0 -> BillListScreen(onBillClick = onBillClick)
                1 -> ComingSoonState(title = "Income tracking is coming soon")
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
