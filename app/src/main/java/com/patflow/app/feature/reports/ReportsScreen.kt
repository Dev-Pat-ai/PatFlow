package com.patflow.app.feature.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.automirrored.rounded.TrendingDown
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import com.patflow.app.core.components.TopBarType
import androidx.compose.foundation.lazy.LazyRow
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.ReportData
import com.patflow.app.domain.model.ReportFilter
import com.patflow.app.domain.model.UserPreferences
import com.patflow.app.domain.model.PaymentHistory
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import androidx.compose.ui.unit.sp
import com.patflow.app.core.components.rememberMarker
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer

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

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Insights", "Categories", "Transactions")

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                AppTopBar(title = "Reports & Analytics", type = TopBarType.Small)
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    divider = {},
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        if (selectedTabIndex < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { 
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                ) 
                            }
                        )
                    }
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
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
                    is ReportsUiState.Success -> ReportContentHybrid(
                        data = state.data,
                        preferences = preferences,
                        selectedTabIndex = selectedTabIndex
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
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = PatFlowSpacing.space2),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { (filter, label) ->
            FilterChip(
                modifier = Modifier.weight(1f),
                selected = currentFilter == filter,
                onClick = { onFilterChange(filter) },
                label = { 
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    ) 
                },
                leadingIcon = if (currentFilter == filter) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                } else null
            )
        }
    }
}

@Composable
private fun ReportContentHybrid(
    data: ReportData,
    preferences: UserPreferences?,
    selectedTabIndex: Int
) {
    val currencyCode = preferences?.profile?.preferredCurrency ?: "PHP"

    when (selectedTabIndex) {
        0 -> OverviewTab(data, currencyCode)
        1 -> InsightsTab(data)
        2 -> CategoriesTab(data, currencyCode)
        3 -> TransactionsTab(data, currencyCode)
    }
}

@Composable
private fun OverviewTab(data: ReportData, currencyCode: String) {
    val trendModelProducer = remember { CartesianChartModelProducer() }
    
    LaunchedEffect(data.trendAnalysis.monthlySpending) {
        if (data.trendAnalysis.monthlySpending.isNotEmpty()) {
            trendModelProducer.runTransaction {
                columnSeries { series(data.trendAnalysis.monthlySpending.values) }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
    ) {
        // 1. Total Balance Card (Design A Style)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = PatFlowShapes.lg,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(PatFlowSpacing.space5)) {
                    Text(text = "Total Balance", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        text = CurrencyFormatter.formatAmount(data.summary.totalIncome - data.summary.totalPaid, currencyCode), 
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(text = "This month", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    
                    Spacer(modifier = Modifier.height(PatFlowSpacing.space4))
                    
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Income", style = MaterialTheme.typography.labelSmall, color = Color(0xFF10B981))
                            Text(text = CurrencyFormatter.formatAmount(data.summary.totalIncome, currencyCode), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Expense", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
                            Text(text = CurrencyFormatter.formatAmount(data.summary.totalPaid, currencyCode), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 2. Spending Trend
        item {
            Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                SectionHeader(title = "Spending Trend")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = PatFlowShapes.lg,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(PatFlowSpacing.space4)) {
                        CartesianChartHost(
                            chart = rememberCartesianChart(
                                rememberColumnCartesianLayer(
                                    columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                                        com.patrykandpatrick.vico.compose.common.component.rememberLineComponent(
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                            thickness = 12.dp,
                                            shape = com.patrykandpatrick.vico.core.common.shape.Shape.rounded(allPercent = 40)
                                        )
                                    )
                                ),
                                startAxis = rememberStartAxis(
                                    label = rememberTextComponent(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textSize = 10.sp
                                    ),
                                    horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Outside,
                                    itemPlacer = VerticalAxis.ItemPlacer.count(count = { 4 })
                                ),
                                bottomAxis = rememberBottomAxis(
                                    label = rememberTextComponent(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textSize = 10.sp
                                    )
                                ),
                                marker = rememberMarker()
                            ),
                            modelProducer = trendModelProducer,
                            modifier = Modifier.height(180.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(PatFlowSpacing.space2))
                        
                        TextButton(
                            onClick = { /* TODO */ },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("View more", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }

        // 3. Top Categories
        item {
            Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                SectionHeader(title = "Top Categories")
                data.categoryAnalysis.spendingByCategory.entries.sortedByDescending { it.value }.take(3).forEach { (category, amount) ->
                    CategoryRow(
                        categoryName = category.name,
                        amount = amount,
                        percentage = if (data.summary.totalPaid > 0) (amount / data.summary.totalPaid * 100).toInt() else 0,
                        currencyCode = currencyCode
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryRow(categoryName: String, amount: Double, percentage: Int, currencyCode: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, PatFlowShapes.md),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.PieChart, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = categoryName, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.End) {
            Text(text = CurrencyFormatter.formatAmount(amount, currencyCode), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(text = "$percentage%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun InsightsTab(data: ReportData) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
    ) {
        item { SectionHeader(title = "Insights") }
        
        // Dynamic Insights based on data
        items(data.insights) { insight ->
            InsightCard(
                title = insight,
                description = "Keep tracking your spending habits to stay on top of your financial goals.",
                icon = Icons.AutoMirrored.Rounded.TrendingUp,
                iconTint = Color(0xFF10B981)
            )
        }

        // Static Coached Insights for visual variety
        item {
            InsightCard(
                title = "Groceries is your top spending category",
                description = "You spent ${CurrencyFormatter.formatAmount(data.categoryAnalysis.spendingByCategory.values.maxOrNull() ?: 0.0)} this month.",
                icon = Icons.Rounded.ShoppingBag,
                iconTint = Color(0xFFF59E0B)
            )
        }
        
        item {
            InsightCard(
                title = "You're 18% of the way to your savings goal",
                description = "Great job managing your expenses! Keep it up!",
                icon = Icons.Rounded.Savings,
                iconTint = Color(0xFF8B5CF6)
            )
        }
    }
}

@Composable
private fun InsightCard(title: String, description: String, icon: ImageVector, iconTint: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = PatFlowShapes.lg,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(PatFlowSpacing.space4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(iconTint.copy(alpha = 0.1f), PatFlowShapes.full),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun CategoriesTab(data: ReportData, currencyCode: String) {
    val categorySpendingModelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(data.categoryAnalysis.spendingByCategory) {
        if (data.categoryAnalysis.spendingByCategory.isNotEmpty()) {
            categorySpendingModelProducer.runTransaction {
                columnSeries { series(data.categoryAnalysis.spendingByCategory.values) }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                SectionHeader(title = "Monthly Comparison")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = PatFlowShapes.lg,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(PatFlowSpacing.space4), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(text = "This Month", style = MaterialTheme.typography.labelSmall)
                                Text(text = CurrencyFormatter.formatAmount(data.summary.totalPaid, currencyCode), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }
                            Text(text = "vs", modifier = Modifier.align(Alignment.CenterVertically), style = MaterialTheme.typography.labelSmall)
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "Last Month", style = MaterialTheme.typography.labelSmall)
                                Text(text = CurrencyFormatter.formatAmount(6420.0, currencyCode), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.AutoMirrored.Rounded.TrendingDown, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "11.8% decrease", color = Color(0xFF10B981), style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                SectionHeader(title = "Detailed Breakdown")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = PatFlowShapes.lg,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(modifier = Modifier.padding(PatFlowSpacing.space4)) {
                        CartesianChartHost(
                            chart = rememberCartesianChart(
                                rememberColumnCartesianLayer(
                                    columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                                        com.patrykandpatrick.vico.compose.common.component.rememberLineComponent(
                                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                                            thickness = 16.dp,
                                            shape = com.patrykandpatrick.vico.core.common.shape.Shape.rounded(allPercent = 40)
                                        )
                                    )
                                ),
                                startAxis = rememberStartAxis(
                                    label = rememberTextComponent(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textSize = 10.sp
                                    ),
                                    itemPlacer = VerticalAxis.ItemPlacer.count(count = { 4 })
                                ),
                                bottomAxis = rememberBottomAxis(
                                    label = rememberTextComponent(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textSize = 10.sp
                                    )
                                ),
                                marker = rememberMarker()
                            ),
                            modelProducer = categorySpendingModelProducer,
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }
        }

        items(data.categoryAnalysis.spendingByCategory.entries.toList()) { (category, amount) ->
            CategoryRow(
                categoryName = category.name,
                amount = amount,
                percentage = if (data.summary.totalPaid > 0) (amount / data.summary.totalPaid * 100).toInt() else 0,
                currencyCode = currencyCode
            )
        }
    }
}

@Composable
private fun TransactionsTab(data: ReportData, currencyCode: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space2),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space1)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = PatFlowSpacing.space2),
                horizontalArrangement = Arrangement.SpaceBetween, 
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "View all", 
                    style = MaterialTheme.typography.labelLarge, 
                    color = MaterialTheme.colorScheme.primary, 
                    modifier = Modifier.clickable { }
                )
            }
        }
        
        if (data.recentTransactions.isEmpty()) {
            item {
                EmptyState(
                    title = "No transactions",
                    description = "Log some payments to see them here.",
                    icon = Icons.Rounded.Payments,
                    modifier = Modifier.height(200.dp)
                )
            }
        } else {
            items(data.recentTransactions) { history ->
                TransactionRow(history)
            }
        }
    }
}

@Composable
private fun TransactionRow(history: PaymentHistory) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF10B981).copy(alpha = 0.1f), PatFlowShapes.full),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.ShoppingCart, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = history.billName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = "Groceries", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "-${CurrencyFormatter.formatAmount(history.payment.amount, history.payment.currencyCode)}", 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.Bold
            )
            Text(text = history.payment.paymentDate.toString(), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
