package com.patflow.app.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowTheme

enum class TopBarType {
    Small, Medium, Large
}

/**
 * Design System §7.8 — AppTopBar.
 * M3 TopAppBar wrapper with consistent styling and support for different sizes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    type: TopBarType = TopBarType.Small,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val titleStyle = when (type) {
        TopBarType.Small, TopBarType.Medium, TopBarType.Large -> MaterialTheme.typography.headlineSmall
    }.copy(fontWeight = FontWeight.SemiBold)

    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        scrolledContainerColor = MaterialTheme.colorScheme.background
    )

    // Standard M3 TopAppBars have 16dp internal horizontal padding.
    val barModifier = modifier

    val titleContent = @Composable {
        Text(
            text = title,
            style = titleStyle
        )
    }

    val navIconContent = @Composable {
        navigationIcon()
    }

    val actionsContent: @Composable RowScope.() -> Unit = {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            actions()
        }
    }

    when (type) {
        TopBarType.Small -> {
            TopAppBar(
                title = titleContent,
                modifier = barModifier,
                navigationIcon = navIconContent,
                actions = actionsContent,
                windowInsets = windowInsets,
                scrollBehavior = scrollBehavior,
                colors = colors
            )
        }
        TopBarType.Medium -> {
            MediumTopAppBar(
                title = titleContent,
                modifier = barModifier,
                navigationIcon = navIconContent,
                actions = actionsContent,
                windowInsets = windowInsets,
                scrollBehavior = scrollBehavior,
                colors = colors
            )
        }
        TopBarType.Large -> {
            LargeTopAppBar(
                title = titleContent,
                modifier = barModifier,
                navigationIcon = navIconContent,
                actions = actionsContent,
                windowInsets = windowInsets,
                scrollBehavior = scrollBehavior,
                colors = colors
            )
        }
    }
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
    SingleChoiceSegmentedButtonRow(
        modifier = modifier,
        space = SegmentedButtonDefaults.BorderWidth // Standard M3 spacing
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                selected = index == selectedIndex,
                onClick = { onOptionSelected(index) },
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                modifier = Modifier.weight(1f), // Equal width tabs
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    activeContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
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
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = selectedRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Medium
                        )
                    )
                },
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
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
            AppTopBar(title = "PatFlow", type = TopBarType.Large)
            Spacer(modifier = Modifier.weight(1f))
            BottomNavigationBar(
                items = items,
                selectedRoute = "dash",
                onItemClick = {}
            )
        }
    }
}
