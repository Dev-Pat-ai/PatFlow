package com.patflow.app.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.automirrored.rounded.Undo
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.theme.PatFlowTheme
import com.patflow.app.core.theme.patFlowCategoryColors
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.BillStatus

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun BillCard(
    name: String,
    amount: Double,
    dueDate: String,
    category: CategoryType,
    status: BillStatus,
    modifier: Modifier = Modifier,
    currencyCode: String = "PHP",
    isSelected: Boolean = false,
    showSelection: Boolean = false,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val categoryColors = patFlowCategoryColors()
    
    val (icon: ImageVector, colors) = remember(category, categoryColors) {
        when (category) {
            CategoryType.ELECTRICITY -> Icons.Rounded.Bolt to categoryColors.electricity
            CategoryType.WATER -> Icons.Rounded.WaterDrop to categoryColors.water
            CategoryType.INTERNET -> Icons.Rounded.Wifi to categoryColors.internet
            CategoryType.RENT -> Icons.Rounded.House to categoryColors.rent
            CategoryType.PHONE -> Icons.Rounded.Smartphone to categoryColors.phone
            CategoryType.INSURANCE -> Icons.Rounded.Shield to categoryColors.insurance
            CategoryType.TUITION -> Icons.Rounded.School to categoryColors.tuition
            CategoryType.SUBSCRIPTION -> Icons.Rounded.Subscriptions to categoryColors.subscription
            CategoryType.LOAN -> Icons.Rounded.AccountBalance to categoryColors.loan
            CategoryType.SAVINGS -> Icons.Rounded.Savings to categoryColors.savings
            CategoryType.HOA_FEES -> Icons.Rounded.Apartment to categoryColors.hoaFees
            
            CategoryType.SALARY -> Icons.Rounded.Work to categoryColors.salary
            CategoryType.FREELANCE -> Icons.Rounded.LaptopMac to categoryColors.freelance
            CategoryType.BUSINESS -> Icons.Rounded.Store to categoryColors.business
            CategoryType.ALLOWANCE -> Icons.Rounded.ChildCare to categoryColors.allowance
            CategoryType.BONUS -> Icons.Rounded.Celebration to categoryColors.bonus
            CategoryType.COMMISSION -> Icons.AutoMirrored.Rounded.TrendingUp to categoryColors.commission
            CategoryType.INVESTMENT -> Icons.Rounded.AccountBalanceWallet to categoryColors.investment
            CategoryType.CASHBACK -> Icons.Rounded.Payments to categoryColors.cashback
            CategoryType.REFUND -> Icons.AutoMirrored.Rounded.Undo to categoryColors.refund
            CategoryType.GIFT -> Icons.Rounded.CardGiftcard to categoryColors.gift
            CategoryType.OTHER -> Icons.Rounded.AddCircle to categoryColors.other
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = PatFlowShapes.lg,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer 
                             else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(PatFlowSpacing.space4)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
            ) {
                AnimatedVisibility(
                    visible = showSelection,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { onClick() }
                    )
                }

                // Category Icon
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(color = colors.containerColor, shape = PatFlowShapes.full),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = colors.onColor
                    )
                }

                // Name and Category
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = category.name.lowercase().replaceFirstChar { it.titlecase() },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Amount
                Text(
                    text = CurrencyFormatter.formatAmount(amount, currencyCode),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFeatureSettings = "tnum"
                    ),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusChip(status = status)
                Text(
                    text = when(status) {
                        BillStatus.PAID -> "Paid $dueDate"
                        BillStatus.OVERDUE -> "Due $dueDate (Overdue)"
                        else -> "Due $dueDate"
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BillCardPreview() {
    PatFlowTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BillCard(
                name = "Meralco",
                amount = 4500.0,
                dueDate = "Jul 18, 2026",
                category = CategoryType.ELECTRICITY,
                status = BillStatus.OVERDUE
            )
            BillCard(
                name = "Netflix",
                amount = 549.0,
                dueDate = "Jul 10, 2026",
                category = CategoryType.SUBSCRIPTION,
                status = BillStatus.PAID
            )
        }
    }
}
