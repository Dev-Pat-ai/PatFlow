package com.patflow.app.feature.reports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
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

@Composable
private fun ReportFilterSection(
    currentFilter: ReportFilter,
    onFilterChange: (ReportFilter) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = when (currentFilter) {
            ReportFilter.ThisMonth -> 0
            ReportFilter.Last3Months -> 1
            ReportFilter.Last6Months -> 2
            ReportFilter.ThisYear -> 3
            is ReportFilter.Custom -> 4
        },
        edgePadding = PatFlowSpacing.space4,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        divider = {}
    ) {
        Tab(selected = currentFilter == ReportFilter.ThisMonth, onClick = { onFilterChange(ReportFilter.ThisMonth) }) {
            Text(text = "This Month", modifier = Modifier.padding(vertical = 12.dp))
        }
        Tab(selected = currentFilter == ReportFilter.Last3Months, onClick = { onFilterChange(ReportFilter.Last3Months) }) {
            Text(text = "Last 3M", modifier = Modifier.padding(vertical = 12.dp))
        }
        Tab(selected = currentFilter == ReportFilter.Last6Months, onClick = { onFilterChange(ReportFilter.Last6Months) }) {
            Text(text = "Last 6M", modifier = Modifier.padding(vertical = 12.dp))
        }
        Tab(selected = currentFilter == ReportFilter.ThisYear, onClick = { onFilterChange(ReportFilter.ThisYear) }) {
            Text(text = "This Year", modifier = Modifier.padding(vertical = 12.dp))
        }
        Tab(
            selected = currentFilter is ReportFilter.Custom,
            onClick = { 
                // Custom range picker logic moves to Phase 7: Personalization & Data Management
            }
        ) {
            Text(text = "Custom", modifier = Modifier.padding(vertical = 12.dp))
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
    Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
        SectionHeader(title = "Financial Summary")
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
        Row(horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
            SummaryCard(
                title = "Expenses",
                value = CurrencyFormatter.formatAmount(data.summary.totalExpenses, currencyCode),
                icon = Icons.Rounded.CalendarToday,
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Total Paid",
                value = CurrencyFormatter.formatAmount(data.summary.totalPaid, currencyCode),
                icon = Icons.Rounded.Payments,
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
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
