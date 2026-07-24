package com.patflow.app.feature.reports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.ReportData
import com.patflow.app.domain.model.ReportFilter
import com.patflow.app.domain.model.UserPreferences
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    modifier: Modifier = Modifier,
    viewModel: ReportsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val preferences by viewModel.userPreferences.collectAsState()

    Scaffold(
        topBar = { AppTopBar(title = "Reports & Analytics") }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = modifier.padding(padding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                ReportFilterSection(
                    currentFilter = filter,
                    onFilterChange = viewModel::onFilterChange
                )

                when (val state = uiState) {
                    ReportsUiState.Loading -> LoadingState()
                    is ReportsUiState.Success -> ReportContent(
                        data = state.data,
                        preferences = preferences
                    )
                    is ReportsUiState.Error -> FullScreenError(
                        title = "Error",
                        description = state.message
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReportFilterSection(
    currentFilter: ReportFilter,
    onFilterChange: (ReportFilter) -> Unit
) {
    val filters = listOf(
        ReportFilter.ThisMonth to "This Month",
        ReportFilter.Last3Months to "Last 3M",
        ReportFilter.Last6Months to "Last 6M",
        ReportFilter.ThisYear to "This Year"
    )
    
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = PatFlowSpacing.space2),
        contentPadding = PaddingValues(horizontal = PatFlowSpacing.space4),
        horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
    ) {
        items(filters) { (filter, label) ->
            FilterChip(
                selected = currentFilter == filter,
                onClick = { onFilterChange(filter) },
                label = { Text(text = label) },
                leadingIcon = if (currentFilter == filter) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                } else null
            )
        }
        item {
            FilterChip(
                selected = currentFilter is ReportFilter.Custom,
                onClick = { /* TODO */ },
                label = { Text(text = "Custom") },
                leadingIcon = if (currentFilter is ReportFilter.Custom) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                } else null
            )
        }
    }
}

@Composable
private fun ReportContent(
    data: ReportData,
    preferences: UserPreferences?
) {
    val trendModelProducer = remember { CartesianChartModelProducer() }
    val incomeTrendModelProducer = remember { CartesianChartModelProducer() }
    val categorySpendingModelProducer = remember { CartesianChartModelProducer() }
    val currencyCode = preferences?.profile?.preferredCurrency ?: "PHP"

    LaunchedEffect(data.trendAnalysis.monthlySpending) {
        if (data.trendAnalysis.monthlySpending.isNotEmpty()) {
            trendModelProducer.runTransaction {
                lineSeries { series(data.trendAnalysis.monthlySpending.values) }
            }
        }
    }

    LaunchedEffect(data.trendAnalysis.monthlyIncome) {
        if (data.trendAnalysis.monthlyIncome.isNotEmpty()) {
            incomeTrendModelProducer.runTransaction {
                lineSeries { series(data.trendAnalysis.monthlyIncome.values) }
            }
        }
    }

    LaunchedEffect(data.categoryAnalysis.spendingByCategory) {
        if (data.categoryAnalysis.spendingByCategory.isNotEmpty()) {
            categorySpendingModelProducer.runTransaction {
                columnSeries { series(data.categoryAnalysis.spendingByCategory.values) }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space5)
    ) {
        // 1. Summary Section
        item {
            FinancialSummarySection(data, currencyCode)
        }

        // 2. Spending Trend
        if (data.trendAnalysis.monthlySpending.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                    SectionHeader(title = "Monthly Spending Trend")
                    CartesianChartHost(
                        chart = rememberCartesianChart(rememberLineCartesianLayer()),
                        modelProducer = trendModelProducer,
                        modifier = Modifier.height(200.dp)
                    )
                }
            }
        }

        // 3. Income Trend
        if (data.trendAnalysis.monthlyIncome.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                    SectionHeader(title = "Monthly Income Trend")
                    CartesianChartHost(
                        chart = rememberCartesianChart(rememberLineCartesianLayer()),
                        modelProducer = incomeTrendModelProducer,
                        modifier = Modifier.height(200.dp)
                    )
                }
            }
        }

        // 4. Category Breakdown
        item {
            CategoryBreakdownSection(data, currencyCode, categorySpendingModelProducer)
        }

        // 5. Performance & Insights
        item {
            PerformanceSection(data)
        }
    }
}

@Composable
private fun FinancialSummarySection(data: ReportData, currencyCode: String) {
    val totalExpenses = data.summary.totalExpenses
    val totalPaid = data.summary.totalPaid
    val utilization = remember(totalPaid, data.summary.totalBudget) {
        if (data.summary.totalBudget > 0) (totalPaid / data.summary.totalBudget) * 100 else 0.0
    }

    Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
        SectionHeader(title = "Financial Summary")
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = PatFlowShapes.lg,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(PatFlowSpacing.space4), verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)) {
                Row(horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                    SummaryCard(
                        title = "Total Income",
                        value = CurrencyFormatter.formatAmount(data.summary.totalIncome, currencyCode),
                        icon = Icons.Rounded.Payments,
                        modifier = Modifier.weight(1f),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    SummaryCard(
                        title = "Net Flow",
                        value = CurrencyFormatter.formatAmount(data.summary.netCashFlow, currencyCode),
                        icon = Icons.Rounded.Payments,
                        modifier = Modifier.weight(1f),
                        containerColor = if (data.summary.netCashFlow >= 0) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                        contentColor = if (data.summary.netCashFlow >= 0) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                
                Row(horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Text(text = "Budget", style = MaterialTheme.typography.labelSmall)
                        Text(text = CurrencyFormatter.formatAmount(data.summary.totalBudget, currencyCode), style = MaterialTheme.typography.titleMedium)
                    }
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Text(text = "Utilization", style = MaterialTheme.typography.labelSmall)
                        Text(text = "${utilization.toInt()}%", style = MaterialTheme.typography.titleMedium, color = if (utilization > 100) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
                    }
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Text(text = "Saved", style = MaterialTheme.typography.labelSmall)
                        Text(text = CurrencyFormatter.formatAmount(data.summary.totalSaved, currencyCode), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }

        StatisticCard(
            title = "Outstanding Balance",
            value = CurrencyFormatter.formatAmount(data.summary.outstandingBalance, currencyCode),
            subtitle = "${data.summary.totalBills} bills · ${data.summary.totalPayments} payments",
            icon = Icons.AutoMirrored.Rounded.ReceiptLong
        )
    }
}

@Composable
private fun CategoryBreakdownSection(
    data: ReportData,
    currencyCode: String,
    modelProducer: CartesianChartModelProducer
) {
    Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
        SectionHeader(title = "Category Breakdown")
        
        if (data.categoryAnalysis.spendingByCategory.isNotEmpty()) {
            CartesianChartHost(
                chart = rememberCartesianChart(rememberColumnCartesianLayer()),
                modelProducer = modelProducer,
                modifier = Modifier.height(200.dp)
            )

            data.categoryAnalysis.highestSpendingCategory?.let {
                StatisticCard(
                    title = "Highest Spending",
                    value = it.name,
                    subtitle = "${CurrencyFormatter.formatAmount(data.categoryAnalysis.spendingByCategory[it] ?: 0.0, currencyCode)} spent",
                    icon = Icons.Rounded.PieChart
                )
            }
        } else {
            EmptyState(
                title = "No category data",
                description = "Log some payments to see category analysis.",
                icon = Icons.Rounded.PieChart,
                modifier = Modifier.height(150.dp)
            )
        }
    }
}

@Composable
private fun PerformanceSection(data: ReportData) {
    Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
        SectionHeader(title = "Insights & Performance")
        
        data.insights.forEach { insight ->
            Text(
                text = "• $insight",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        Spacer(modifier = Modifier.height(PatFlowSpacing.space2))
        
        Row(horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
            SummaryCard(
                title = "On-Time",
                value = "${data.performance.onTimePercentage.toInt()}%",
                icon = Icons.Rounded.CalendarToday,
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Overdue",
                value = data.performance.overdueBillsCount.toString(),
                icon = Icons.Rounded.CalendarToday,
                modifier = Modifier.weight(1f),
                containerColor = if (data.performance.overdueBillsCount > 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface,
                contentColor = if (data.performance.overdueBillsCount > 0) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
