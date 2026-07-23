package com.patflow.app.feature.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Unarchive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CurrencyFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetDetailScreen(
    onNavigateBack: () -> Unit,
    onEditClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BudgetDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Budget Details",
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (uiState is BudgetDetailUiState.Success) {
                        val budget = (uiState as BudgetDetailUiState.Success).analytics.budget
                        IconButton(onClick = { viewModel.toggleArchive(!budget.isArchived) }) {
                            Icon(
                                if (budget.isArchived) Icons.Rounded.Unarchive else Icons.Rounded.Archive,
                                contentDescription = "Archive"
                            )
                        }
                        IconButton(onClick = { 
                            viewModel.deleteBudget()
                            onNavigateBack()
                        }) {
                            Icon(Icons.Rounded.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                        }
                        IconButton(onClick = { onEditClick(budget.id) }) {
                            Icon(Icons.Rounded.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                BudgetDetailUiState.Loading -> LoadingState()
                is BudgetDetailUiState.Success -> {
                    val analytics = state.analytics
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(PatFlowSpacing.space4),
                        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space5)
                    ) {
                        // 1. Progress Gauge (Simulated with CircularProgress)
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    progress = { analytics.percentageUsed.coerceIn(0f, 1f) },
                                    modifier = Modifier.size(200.dp),
                                    strokeWidth = 12.dp,
                                    color = if (analytics.isOverspent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "${(analytics.percentageUsed * 100).toInt()}%",
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "of total",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            
                            Text(
                                text = analytics.budget.name,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }

                        // 2. Summary Statistics
                        Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                            SectionHeader(title = "Summary")
                            StatisticRow(
                                label = "Total Budget",
                                value = CurrencyFormatter.formatAmount(analytics.budget.totalAmount, analytics.budget.currencyCode)
                            )
                            StatisticRow(
                                label = "Amount Used",
                                value = CurrencyFormatter.formatAmount(analytics.amountUsed, analytics.budget.currencyCode),
                                valueColor = if (analytics.isOverspent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )
                            StatisticRow(
                                label = "Remaining",
                                value = CurrencyFormatter.formatAmount(analytics.remainingAmount, analytics.budget.currencyCode),
                                valueColor = if (analytics.remainingAmount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                            )
                        }

                        // 3. Daily Tracking
                        Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                            SectionHeader(title = "Daily Tracking")
                            StatisticRow(
                                label = "Daily Allowance",
                                value = CurrencyFormatter.formatAmount(analytics.dailyAllowance, analytics.budget.currencyCode),
                                subtitle = "Budget / Remaining Days"
                            )
                            StatisticRow(
                                label = "Avg Daily Spent",
                                value = CurrencyFormatter.formatAmount(analytics.averageDailySpending, analytics.budget.currencyCode)
                            )
                            StatisticRow(
                                label = "Forecast Spent",
                                value = CurrencyFormatter.formatAmount(analytics.forecastEndAmount, analytics.budget.currencyCode),
                                subtitle = "Estimated total at end"
                            )
                        }

                        // 4. Time Range
                        Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                            SectionHeader(title = "Period")
                            Text(
                                text = "${analytics.budget.startDate} — ${analytics.budget.endDate}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "${analytics.remainingDays} days remaining",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                is BudgetDetailUiState.Error -> FullScreenError(title = "Error", description = state.message)
            }
        }
    }
}

@Composable
private fun StatisticRow(
    label: String,
    value: String,
    subtitle: String? = null,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (subtitle != null) {
                Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Text(text = value, style = MaterialTheme.typography.titleMedium, color = valueColor, fontWeight = FontWeight.Bold)
    }
}
