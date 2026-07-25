package com.patflow.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Pending
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowTheme
import com.patflow.app.core.theme.StatusColorPair
import com.patflow.app.core.theme.patFlowStatusColors
import com.patflow.app.domain.model.BillStatus

@Composable
fun StatusChip(
    status: BillStatus,
    modifier: Modifier = Modifier
) {
    val statusColors = patFlowStatusColors()
    
    val (label, icon, colors) = when (status) {
        BillStatus.PAID -> Triple("Paid", Icons.Rounded.CheckCircle, statusColors.paid)
        BillStatus.UNPAID -> Triple("Unpaid", Icons.Rounded.Schedule, statusColors.unpaid)
        BillStatus.OVERDUE -> Triple("Overdue", Icons.Rounded.Error, statusColors.overdue)
    }

    StatusChipContent(
        label = label,
        icon = icon,
        colors = colors,
        modifier = modifier
    )
}

@Composable
private fun StatusChipContent(
    label: String,
    icon: ImageVector,
    colors: StatusColorPair,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(color = colors.containerColor, shape = PatFlowShapes.sm)
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .widthIn(min = 100.dp), // Ensure a consistent minimum width for all status labels
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = colors.onColor
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
            color = colors.onColor,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusChipPreview() {
    PatFlowTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatusChip(status = BillStatus.PAID)
            StatusChip(status = BillStatus.UNPAID)
            StatusChip(status = BillStatus.OVERDUE)
        }
    }
}
