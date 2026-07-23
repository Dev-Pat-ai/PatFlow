package com.patflow.app.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppButton
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.BillCard
import com.patflow.app.core.components.CategoryType
import com.patflow.app.core.components.EmptyState
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
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onBillClick: (Long) -> Unit,
    onPaymentClick: (Long) -> Unit,
    onAddBillClick: () -> Unit,
    onLogPaymentClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val preferences by viewModel.userPreferences.collectAsState()

    val speedDialActions = remember(onAddBillClick, onLogPaymentClick) {
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

    Scaffold(
        topBar = { AppTopBar(title = "Dashboard") },
        floatingActionButton = { SpeedDialFab(actions = speedDialActions) }
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
                    onPaymentClick = onPaymentClick
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
            .padding(PatFlowSpacing.space4),
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
    onPaymentClick: (Long) -> Unit
) {
    val trendModelProducer = remember { CartesianChartModelProducer() }
    val categoryModelProducer = remember { CartesianChartModelProducer() }
    val currencyCode = preferences?.profile?.preferredCurrency ?: "PHP"

    LaunchedEffect(data.spendingTrend) {
        if (data.spendingTrend.isNotEmpty()) {
            trendModelProducer.runTransaction {
                lineSeries { series(data.spendingTrend.values) }
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
        contentPadding = PaddingValues(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
    ) {
        // Summary Row
        item {
            SummaryRow(data, currencyCode)
        }

        // Spending Trend
        if (data.spendingTrend.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                    SectionHeader(title = "Monthly Spending")
                    CartesianChartHost(
                        chart = rememberCartesianChart(rememberLineCartesianLayer()),
                        modelProducer = trendModelProducer,
                        modifier = Modifier.height(200.dp)
                    )
                }
            }
        }

        // Category Breakdown
        if (data.spendingByCategory.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                    SectionHeader(title = "Spending by Category")
                    CartesianChartHost(
                        chart = rememberCartesianChart(rememberColumnCartesianLayer()),
                        modelProducer = categoryModelProducer,
                        modifier = Modifier.height(200.dp)
                    )
                }
            }
        }

        // Insights Section
        if (data.insights.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
                ) {
                    SectionHeader(title = "Insights")
                    data.insights.forEach { insight ->
                        Text(
                            text = "• $insight",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Upcoming Bills
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

        // Recent Payments
        if (data.recentPayments.isNotEmpty()) {
            item { SectionHeader(title = "Recent Payments") }
            items(data.recentPayments) { item ->
                RecentPaymentItem(
                    history = item,
                    onClick = { onPaymentClick(item.payment.id) }
                )
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
                title = "Total Due",
                value = CurrencyFormatter.formatAmount(data.totalBillsThisMonth, currencyCode),
                icon = Icons.Rounded.CalendarToday,
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Paid",
                value = CurrencyFormatter.formatAmount(data.totalPaidThisMonth, currencyCode),
                icon = Icons.Rounded.Payments,
                modifier = Modifier.weight(1f),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                containerColor = if (data.overdueBillsCount > 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface,
                contentColor = if (data.overdueBillsCount > 0) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface
            )
            SummaryCard(
                title = "Upcoming",
                value = data.upcomingBillsCount.toString(),
                icon = Icons.Rounded.CalendarToday,
                modifier = Modifier.weight(1f)
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
            Text(text = history.billName, style = MaterialTheme.typography.titleMedium)
            Text(
                text = history.payment.paymentDate.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = CurrencyFormatter.formatAmount(history.payment.amount, history.payment.currencyCode),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
