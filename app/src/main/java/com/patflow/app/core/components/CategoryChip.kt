package com.patflow.app.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.Apartment
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.House
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material.icons.rounded.Subscriptions
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patflow.app.core.theme.CategoryColorPair
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowTheme
import com.patflow.app.core.theme.patFlowCategoryColors

/**
 * Design System §10 — Category Branding.
 */
enum class CategoryType {
    ELECTRICITY, WATER, INTERNET, RENT, PHONE, INSURANCE, TUITION, SUBSCRIPTION, LOAN, SAVINGS, HOA_FEES
}

@Composable
fun CategoryChip(
    category: CategoryType,
    modifier: Modifier = Modifier
) {
    val categoryColors = patFlowCategoryColors()
    
    val (label, icon, colors) = remember(category, categoryColors) {
        when (category) {
            CategoryType.ELECTRICITY -> Triple("Electricity", Icons.Rounded.Bolt, categoryColors.electricity)
            CategoryType.WATER -> Triple("Water", Icons.Rounded.WaterDrop, categoryColors.water)
            CategoryType.INTERNET -> Triple("Internet", Icons.Rounded.Wifi, categoryColors.internet)
            CategoryType.RENT -> Triple("Rent", Icons.Rounded.House, categoryColors.rent)
            CategoryType.PHONE -> Triple("Phone", Icons.Rounded.Smartphone, categoryColors.phone)
            CategoryType.INSURANCE -> Triple("Insurance", Icons.Rounded.Shield, categoryColors.insurance)
            CategoryType.TUITION -> Triple("Tuition", Icons.Rounded.School, categoryColors.tuition)
            CategoryType.SUBSCRIPTION -> Triple("Subscription", Icons.Rounded.Subscriptions, categoryColors.subscription)
            CategoryType.LOAN -> Triple("Loan", Icons.Rounded.AccountBalance, categoryColors.loan)
            CategoryType.SAVINGS -> Triple("Savings", Icons.Rounded.Savings, categoryColors.savings)
            CategoryType.HOA_FEES -> Triple("HOA Fees", Icons.Rounded.Apartment, categoryColors.hoaFees)
        }
    }

    CategoryChipContent(
        label = label,
        icon = icon,
        colors = colors,
        modifier = modifier
    )
}

@Composable
private fun CategoryChipContent(
    label: String,
    icon: ImageVector,
    colors: CategoryColorPair,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(color = colors.containerColor, shape = PatFlowShapes.full)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = colors.onColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = colors.onColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipPreview() {
    PatFlowTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryType.entries.forEach {
                CategoryChip(category = it)
            }
        }
    }
}
