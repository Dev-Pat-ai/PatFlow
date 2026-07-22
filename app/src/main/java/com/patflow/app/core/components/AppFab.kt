package com.patflow.app.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.theme.PatFlowTheme

/**
 * Design System §7.7 — AppFab.
 * Standard FAB wrapper using the signature AmberFlow (tertiaryContainer).
 */
@Composable
fun AppFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Rounded.Add,
    contentDescription: String? = "Add"
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        shape = PatFlowShapes.full
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

/**
 * Design System §15 — SpeedDialFab.
 * Expandable FAB with mini-actions.
 */
@Composable
fun SpeedDialFab(
    actions: List<SpeedDialAction>,
    modifier: Modifier = Modifier,
    mainActionIcon: ImageVector = Icons.Rounded.Add
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 45f else 0f, label = "fabRotation")

    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {
        // Scrim
        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f))
                    .clickable { expanded = false }
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3),
            modifier = Modifier.padding(bottom = 72.dp, end = 16.dp) // Adjusted to sit above main FAB
        ) {
            actions.forEach { action ->
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    SpeedDialItem(action = action, onClick = {
                        expanded = false
                        action.onClick()
                    })
                }
            }
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.padding(16.dp),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            shape = PatFlowShapes.full
        ) {
            Icon(
                imageVector = if (expanded) Icons.Rounded.Close else mainActionIcon,
                contentDescription = if (expanded) "Close" else "Open actions",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
private fun SpeedDialItem(
    action: SpeedDialAction,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        // Label
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, shape = PatFlowShapes.full)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = action.label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Mini FAB
        SmallFloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            shape = PatFlowShapes.full
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

data class SpeedDialAction(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
private fun AppFabPreview() {
    PatFlowTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            val actions = listOf(
                SpeedDialAction("Add Bill", Icons.Rounded.ReceiptLong) {},
                SpeedDialAction("Log Payment", Icons.Rounded.Payments) {},
                SpeedDialAction("Add Income", Icons.Rounded.TrendingUp) {},
                SpeedDialAction("Add Savings", Icons.Rounded.Savings) {}
            )
            
            SpeedDialFab(actions = actions)
        }
    }
}
