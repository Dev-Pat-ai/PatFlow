package com.patflow.app.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppButton
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.TopBarType
import com.patflow.app.core.components.BillCard
import com.patflow.app.core.components.BudgetProgressCard
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.components.SavingsGoalProgressCard
import com.patflow.app.core.components.FullScreenError
import com.patflow.app.core.components.SectionHeader
import com.patflow.app.core.components.SkeletonBox
import com.patflow.app.core.components.SpeedDialAction
import com.patflow.app.core.components.SpeedDialFab
import com.patflow.app.core.components.SummaryCard
import com.patflow.app.core.theme.PatFlowShapes
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.DashboardData
import com.patflow.app.domain.model.PaymentHistory
import com.patflow.app.domain.model.UserPreferences
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import androidx.compose.ui.unit.sp
import com.patflow.app.core.components.rememberMarker
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onBillClick: (Long) -> Unit,
    onPaymentClick: (Long) -> Unit,
    onBudgetClick: (Long) -> Unit,
    onGoalClick: (Long) -> Unit,
    onAddBillClick: () -> Unit,
    onLogPaymentClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val preferences by viewModel.userPreferences.collectAsState()

    val speedDialActions by remember {
        derivedStateOf {
            listOf(
                SpeedDialAction(
                    label = "Add Bill",
                    icon = Icons.AutoMirrored.Rounded.ReceiptLong,
                    onClick = onAddBillClick
                ),
                SpeedDialAction(
                    label = "Log Payment",
                    icon = Icons.Rounded.Payments,
                    onClick = onLogPaymentClick
                )
            )
        }
    }

    Scaffold(
        topBar = { 
            AppTopBar(
                title = "Dashboard",
                type = TopBarType.Small,
                actions = {
                    IconButton(onClick = { /* TODO: Search */ }) {
                        Icon(Icons.Rounded.Search, contentDescription = "Search")
                    }
                }
            ) 
        },
        floatingActionButton = { SpeedDialFab(actions = speedDialActions) },
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0)
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = modifier.padding(padding)
        ) {
            when (val state = uiState) {
                DashboardUiState.Loading -> DashboardLoading()
                DashboardUiState.Empty -> DashboardEmptyState(onAddBillClick)
                is DashboardUiState.Success -> DashboardContent(
                    data = state.data,
                    preferences = preferences,
                    onBillClick = onBillClick,
                    onPaymentClick = onPaymentClick,
                    onBudgetClick = onBudgetClick,
                    onGoalClick = onGoalClick
                )
                is DashboardUiState.Error -> FullScreenError(
                    title = "Error",
                    description = state.message
                )
            }
        }
    }
}

@Composable
private fun DashboardLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
            SkeletonBox(modifier = Modifier.weight(1f), height = 100.dp, shape = PatFlowShapes.lg)
            SkeletonBox(modifier = Modifier.weight(1f), height = 100.dp, shape = PatFlowShapes.lg)
        }
        SkeletonBox(height = 150.dp, shape = PatFlowShapes.lg)
        repeat(3) {
            SkeletonBox(height = 80.dp, shape = PatFlowShapes.lg)
        }
    }
}

@Composable
private fun DashboardEmptyState(onAddBillClick: () -> Unit) {
    EmptyState(
        title = "Welcome to PatFlow!",
        description = "Add your first bill to start tracking your expenses.",
        icon = Icons.AutoMirrored.Rounded.ReceiptLong,
        action = {
            AppButton(onClick = onAddBillClick) {
                Text("Add Bill")
            }
        }
    )
}

@Composable
private fun DashboardContent(
    data: DashboardData,
    preferences: UserPreferences?,
    onBillClick: (Long) -> Unit,
    onPaymentClick: (Long) -> Unit,
    onBudgetClick: (Long) -> Unit,
    onGoalClick: (Long) -> Unit
) {
    val trendModelProducer = remember { CartesianChartModelProducer() }
    val categoryModelProducer = remember { CartesianChartModelProducer() }
    val currencyCode = preferences?.profile?.preferredCurrency ?: "PHP"

    LaunchedEffect(data.spendingTrend) {
        if (data.spendingTrend.isNotEmpty()) {
            trendModelProducer.runTransaction {
                columnSeries { series(data.spendingTrend.values) }
            }
        }
    }

    LaunchedEffect(data.spendingByCategory) {
        if (data.spendingByCategory.isNotEmpty()) {
            categoryModelProducer.runTransaction {
                columnSeries { series(data.spendingByCategory.values) }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
    ) {
        // 1. Financial Summary Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                SectionHeader(title = "Financial Summary")
                SummaryRow(data, currencyCode)
            }
        }

        // 2. Upcoming Bills
        if (data.upcomingBills.isNotEmpty()) {
            item { SectionHeader(title = "Upcoming Bills") }
            items(data.upcomingBills) { item ->
                BillCard(
                    name = item.bill.name,
                    amount = item.currentCycle?.amountDue ?: item.bill.defaultAmount,
                    dueDate = item.currentCycle?.dueDate?.toString() ?: "N/A",
                    category = CategoryMapper.mapToType(item.bill.category.name),
                    status = item.currentCycle?.status ?: BillStatus.UNPAID,
                    onClick = { onBillClick(item.bill.id) }
                )
            }
        }

        // 3. Recent Transactions (Payments)
        if (data.recentPayments.isNotEmpty()) {
            item { SectionHeader(title = "Recent Transactions") }
            items(data.recentPayments) { item ->
                RecentPaymentItem(
                    history = item,
                    onClick = { onPaymentClick(item.payment.id) }
                )
            }
        }

        // 4. Budget Progress
        data.budgetAnalytics?.let { analytics ->
            item {
                Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                    SectionHeader(title = "Budget Progress")
                    BudgetProgressCard(
                        name = analytics.budget.name,
                        totalAmount = analytics.budget.totalAmount,
                        amountUsed = analytics.amountUsed,
                        percentageUsed = analytics.percentageUsed,
                        currencyCode = currencyCode,
                        onClick = { onBudgetClick(analytics.budget.id) }
                    )
                }
            }
        }

        // 5. Savings Progress
        if (data.savingsGoals.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                    SectionHeader(title = "Savings Progress")
                    data.savingsGoals.forEach { goal ->
                        SavingsGoalProgressCard(
                            name = goal.goal.name,
                            targetAmount = goal.goal.targetAmount,
                            currentAmount = goal.goal.currentAmount,
                            percentageUsed = goal.progressPercentage,
                            currencyCode = currencyCode,
                            onClick = { onGoalClick(goal.goal.id) }
                        )
                    }
                }
            }
        }

        // 6. Insights
        if (data.insights.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f),
                            shape = PatFlowShapes.lg
                        )
                        .padding(horizontal = PatFlowSpacing.space5, vertical = PatFlowSpacing.space4),
                    verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
                ) {
                    SectionHeader(title = "Financial Insights")
                    data.insights.forEach { insight ->
                        Text(
                            text = "• $insight",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }

        // 7. Visual Analytics (Spending Trend & Category) - Optional extra visual polish
        if (data.spendingTrend.isNotEmpty() || data.spendingByCategory.isNotEmpty()) {
            item { SectionHeader(title = "Analytics") }
            
            if (data.spendingTrend.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = PatFlowShapes.lg,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Column(modifier = Modifier.padding(PatFlowSpacing.space4)) {
                            Text(text = "Monthly Spending", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(PatFlowSpacing.space2))
                            CartesianChartHost(
                                chart = rememberCartesianChart(
                                    rememberColumnCartesianLayer(
                                        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                                            rememberLineComponent(
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
                                modifier = Modifier.height(160.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(data: DashboardData, currencyCode: String) {
    Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
        ) {
            SummaryCard(
                title = "Monthly Income",
                value = CurrencyFormatter.formatAmount(data.totalIncomeThisMonth, currencyCode),
                icon = Icons.Rounded.Payments,
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
                iconTint = MaterialTheme.colorScheme.primary
            )
            SummaryCard(
                title = "Net Cash Flow",
                value = CurrencyFormatter.formatAmount(data.netCashFlow, currencyCode),
                icon = Icons.Rounded.Payments,
                modifier = Modifier.weight(1f),
                containerColor = if (data.netCashFlow >= 0) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.errorContainer,
                contentColor = if (data.netCashFlow >= 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onErrorContainer,
                iconTint = if (data.netCashFlow >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
        ) {
            SummaryCard(
                title = "Total Due",
                value = CurrencyFormatter.formatAmount(data.totalBillsThisMonth, currencyCode),
                icon = Icons.Rounded.CalendarToday,
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
            SummaryCard(
                title = "Paid",
                value = CurrencyFormatter.formatAmount(data.totalPaidThisMonth, currencyCode),
                icon = Icons.Rounded.Payments,
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                iconTint = MaterialTheme.colorScheme.primary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
        ) {
            SummaryCard(
                title = "Overdue",
                value = data.overdueBillsCount.toString(),
                icon = Icons.Rounded.Error,
                modifier = Modifier.weight(1f),
                containerColor = if (data.overdueBillsCount > 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceContainer,
                contentColor = if (data.overdueBillsCount > 0) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface,
                iconTint = if (data.overdueBillsCount > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
            )
            SummaryCard(
                title = "Upcoming",
                value = data.upcomingBillsCount.toString(),
                icon = Icons.Rounded.CalendarToday,
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        }
    }
}

@Composable
private fun RecentPaymentItem(
    history: PaymentHistory,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = PatFlowSpacing.space2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = PatFlowShapes.full
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Payments,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(24.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = history.billName, 
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = history.payment.paymentDate.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = CurrencyFormatter.formatAmount(history.payment.amount, history.payment.currencyCode),
            style = MaterialTheme.typography.titleMedium.copy(fontFeatureSettings = "tnum"),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
