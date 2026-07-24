package com.patflow.app.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.automirrored.rounded.Undo
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Apartment
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.ChildCare
import androidx.compose.material.icons.rounded.House
import androidx.compose.material.icons.rounded.LaptopMac
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material.icons.rounded.Store
import androidx.compose.material.icons.rounded.Subscriptions
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.theme.PatFlowTheme
import com.patflow.app.core.theme.patFlowCategoryColors
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.BillStatus

/**
 * Design System §7.2 — BillCard.
 * The central list item component for displaying bill cycles.
 */
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
                             else MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(PatFlowSpacing.space4)
                .fillMaxWidth(),
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
                    .size(40.dp)
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

            // Name and Due Date
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Due · $dueDate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Amount and Status
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.widthIn(min = 80.dp)
            ) {
                Text(
                    text = CurrencyFormatter.formatAmount(amount, currencyCode),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFeatureSettings = "tnum"
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(PatFlowSpacing.space1))
                StatusChip(status = status)
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
                dueDate = "Oct 15",
                category = CategoryType.ELECTRICITY,
                status = BillStatus.UNPAID
            )
            BillCard(
                name = "PLDT Home Fibr",
                amount = 1899.0,
                dueDate = "Oct 12",
                category = CategoryType.INTERNET,
                status = BillStatus.PAID
            )
            BillCard(
                name = "Home Credit Loan",
                amount = 2500.0,
                dueDate = "Oct 05",
                category = CategoryType.LOAN,
                status = BillStatus.OVERDUE
            )
        }
    }
}
