package com.patflow.app.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowTheme

/**
 * Design System §7.8 — AppTopBar.
 * M3 CenterAlignedTopAppBar wrapper with consistent styling and scroll behavior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

/**
 * Design System §7.8 — Segmented Control.
 * Used for top-level toggles (e.g., Bills | Income | Savings).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                selected = index == selectedIndex,
                onClick = { onOptionSelected(index) },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}

/**
 * Design System §7.8 — BottomNavigationBar.
 * M3 NavigationBar wrapper with the required active-indicator pill.
 */
@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    selectedRoute: String,
    onItemClick: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = NavigationBarDefaults_TonalElevation
    ) {
        items.forEach { item ->
            val isSelected = selectedRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}

// Internal constant to match M3 specs if needed, otherwise uses default.
private val NavigationBarDefaults_TonalElevation = 0.dp // M3 Level 0 at rest per §7.8

data class NavigationItem(
    val label: String,
    val route: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AppNavigationPreview() {
    PatFlowTheme {
        val items = listOf(
            NavigationItem("Dashboard", "dash", Icons.Rounded.Dashboard, Icons.Rounded.Dashboard),
            NavigationItem("Money", "money", Icons.Rounded.AccountBalance, Icons.Rounded.AccountBalance),
            NavigationItem("Calendar", "cal", Icons.Rounded.CalendarMonth, Icons.Rounded.CalendarMonth),
            NavigationItem("Reports", "rep", Icons.Rounded.PieChart, Icons.Rounded.PieChart),
            NavigationItem("Settings", "set", Icons.Rounded.Settings, Icons.Rounded.Settings)
        )
        
        Column {
            AppTopBar(title = "PatFlow")
            Spacer(modifier = Modifier.weight(1f))
            BottomNavigationBar(
                items = items,
                selectedRoute = "dash",
                onItemClick = {}
            )
        }
    }
}
