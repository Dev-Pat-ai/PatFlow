package com.patflow.app.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CurrencyFormatter

/**
 * Design System — Budget Progress Card.
 * Displays budget name, usage progress bar, and amounts.
 */
@Composable
fun BudgetProgressCard(
    name: String,
    totalAmount: Double,
    amountUsed: Double,
    percentageUsed: Float,
    currencyCode: String = "PHP",
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val isOverspent = amountUsed > totalAmount
    val progressColor = if (isOverspent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = PatFlowShapes.lg,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(PatFlowSpacing.space4),
            verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${(percentageUsed * 100).toInt()}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = progressColor,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            LinearProgressIndicator(
                progress = { percentageUsed.coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = progressColor,
                trackColor = progressColor.copy(alpha = 0.2f),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Used: ${CurrencyFormatter.formatAmount(amountUsed, currencyCode)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Total: ${CurrencyFormatter.formatAmount(totalAmount, currencyCode)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
