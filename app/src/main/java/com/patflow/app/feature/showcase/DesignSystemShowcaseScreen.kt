package com.patflow.app.feature.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patflow.app.core.components.AmountTextField
import com.patflow.app.core.components.AppButton
import com.patflow.app.core.components.AppButtonType
import com.patflow.app.core.components.AppDatePickerDialog
import com.patflow.app.core.components.AppModalBottomSheet
import com.patflow.app.core.components.AppSnackbarHost
import com.patflow.app.core.components.AppTextField
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.BillCard
import com.patflow.app.core.components.BillStatus
import com.patflow.app.core.components.BottomNavigationBar
import com.patflow.app.core.components.CategoryChip
import com.patflow.app.core.components.CategoryType
import com.patflow.app.core.components.ConfirmationDialog
import com.patflow.app.core.components.DeleteConfirmationDialog
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.components.NavigationItem
import com.patflow.app.core.components.SkeletonBox
import com.patflow.app.core.components.SpeedDialAction
import com.patflow.app.core.components.SpeedDialFab
import com.patflow.app.core.components.StatisticCard
import com.patflow.app.core.components.StatusChip
import com.patflow.app.core.components.SummaryCard
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CurrencyFormatter
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DesignSystemShowcaseScreen() {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    
    val navItems = listOf(
        NavigationItem("Dashboard", "dash", Icons.Rounded.Dashboard, Icons.Rounded.Dashboard),
        NavigationItem("Money", "money", Icons.Rounded.Payments, Icons.Rounded.Payments),
        NavigationItem("Calendar", "cal", Icons.Rounded.CalendarMonth, Icons.Rounded.CalendarMonth),
        NavigationItem("Reports", "rep", Icons.Rounded.PieChart, Icons.Rounded.PieChart),
        NavigationItem("Settings", "set", Icons.Rounded.Settings, Icons.Rounded.Settings)
    )

    Scaffold(
        topBar = {
            AppTopBar(title = "Design System Showcase")
        },
        bottomBar = {
            BottomNavigationBar(
                items = navItems,
                selectedRoute = "dash",
                onItemClick = {}
            )
        },
        floatingActionButton = {
            val speedDialActions = listOf(
                SpeedDialAction("Add Bill", Icons.Rounded.ReceiptLong) {
                    scope.launch { snackbarHostState.showSnackbar("Add Bill Clicked") }
                },
                SpeedDialAction("Log Payment", Icons.Rounded.Payments) {
                    scope.launch { snackbarHostState.showSnackbar("Log Payment Clicked") }
                },
                SpeedDialAction("Add Income", Icons.Rounded.TrendingUp) {
                    scope.launch { snackbarHostState.showSnackbar("Add Income Clicked") }
                },
                SpeedDialAction("Add Savings", Icons.Rounded.Savings) {
                    scope.launch { snackbarHostState.showSnackbar("Add Savings Clicked") }
                }
            )
            SpeedDialFab(actions = speedDialActions)
        },
        snackbarHost = { AppSnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(PatFlowSpacing.space4),
            verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space5)
        ) {
            // --- Typography ---
            ShowcaseSection(title = "Typography") {
                Text("Display Large", style = MaterialTheme.typography.displayLarge)
                Text("Headline Small", style = MaterialTheme.typography.headlineSmall)
                Text("Title Large", style = MaterialTheme.typography.titleLarge)
                Text("Title Medium", style = MaterialTheme.typography.titleMedium)
                Text("Body Large", style = MaterialTheme.typography.bodyLarge)
                Text("Body Medium", style = MaterialTheme.typography.bodyMedium)
                Text("Label Large", style = MaterialTheme.typography.labelLarge)
                Text("Label Medium", style = MaterialTheme.typography.labelMedium)
                Text("Label Small", style = MaterialTheme.typography.labelSmall)
            }

            // --- Cards ---
            ShowcaseSection(title = "Cards") {
                BillCard(
                    name = "Meralco",
                    amount = 4500.0,
                    dueDate = "Oct 15",
                    category = CategoryType.ELECTRICITY,
                    status = BillStatus.UNPAID
                )
                
                Row(horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)) {
                    SummaryCard(
                        title = "Due Today",
                        value = "3",
                        icon = Icons.Rounded.CalendarToday,
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        title = "Overdue",
                        value = "1",
                        icon = Icons.Rounded.CalendarToday,
                        modifier = Modifier.weight(1f),
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                
                StatisticCard(
                    title = "Total Paid This Month",
                    value = "₱12,450.00",
                    icon = Icons.Rounded.Payments,
                    subtitle = "+12% vs last month"
                )
            }

            // --- Buttons ---
            ShowcaseSection(title = "Buttons") {
                AppButton(onClick = {}, type = AppButtonType.Filled) { Text("Primary Filled") }
                AppButton(onClick = {}, type = AppButtonType.Tonal) { Text("Secondary Tonal") }
                AppButton(onClick = {}, type = AppButtonType.Outlined) { Text("Tertiary Outlined") }
                AppButton(onClick = {}, type = AppButtonType.Text) { Text("Text Button") }
                AppButton(onClick = {}, type = AppButtonType.Filled, enabled = false) { Text("Disabled Button") }
            }

            // --- Actions & Overlays ---
            ShowcaseSection(title = "Actions & Overlays") {
                AppButton(onClick = { showDialog = true }) { Text("Show Confirmation Dialog") }
                AppButton(onClick = { showDeleteDialog = true }, type = AppButtonType.Tonal) { Text("Show Delete Dialog") }
                AppButton(onClick = { showBottomSheet = true }, type = AppButtonType.Outlined) { Text("Show Bottom Sheet") }
                AppButton(onClick = { showDatePicker = true }, type = AppButtonType.Text) { Text("Show Date Picker") }
            }

            // --- Text Fields ---
            ShowcaseSection(title = "Text Fields") {
                var textValue by remember { mutableStateOf("") }
                var amountValue by remember { mutableStateOf("1250.00") }
                
                AppTextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    label = "Standard Input",
                    placeholder = "Type here..."
                )
                
                AmountTextField(
                    value = amountValue,
                    onValueChange = { amountValue = it },
                    label = "Amount Input"
                )
                
                AppTextField(
                    value = "Invalid input",
                    onValueChange = {},
                    label = "Error State",
                    isError = true,
                    helperText = "Something went wrong"
                )
            }

            // --- Chips ---
            ShowcaseSection(title = "Status Chips") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2),
                    verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
                ) {
                    StatusChip(status = BillStatus.PAID)
                    StatusChip(status = BillStatus.PARTIALLY_PAID)
                    StatusChip(status = BillStatus.UNPAID)
                    StatusChip(status = BillStatus.OVERDUE)
                }
            }

            ShowcaseSection(title = "Category Chips") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2),
                    verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space2)
                ) {
                    CategoryType.entries.forEach {
                        CategoryChip(category = it)
                    }
                }
            }

            // --- UI States ---
            ShowcaseSection(title = "Empty State") {
                EmptyState(
                    title = "No data found",
                    description = "Try adjusting your filters or adding a new record.",
                    icon = Icons.Rounded.ReceiptLong,
                    modifier = Modifier.height(300.dp),
                    action = {
                        AppButton(onClick = {}) { Text("Add Item") }
                    }
                )
            }

            // --- Loading State ---
            ShowcaseSection(title = "Loading States") {
                SkeletonBox(height = 24.dp, width = 200.dp)
                SkeletonBox(height = 80.dp)
                SkeletonBox(height = 16.dp, width = 100.dp)
            }

            // --- Helpers ---
            ShowcaseSection(title = "Currency Helpers") {
                Text("Default (PHP): ${CurrencyFormatter.formatAmount(1234.56)}")
                Text("USD: ${CurrencyFormatter.formatAmount(1234.56, "USD")}")
            }
            
            Spacer(modifier = Modifier.height(PatFlowSpacing.space8))
        }
    }

    // --- Dialogs & Sheets ---
    if (showDialog) {
        ConfirmationDialog(
            onDismissRequest = { showDialog = false },
            onConfirm = { 
                showDialog = false
                scope.launch { snackbarHostState.showSnackbar("Confirmed!") }
            },
            title = "Simple Confirmation",
            text = "Are you sure you want to proceed with this action?"
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { showDeleteDialog = false },
            onDelete = { 
                showDeleteDialog = false
                scope.launch { snackbarHostState.showSnackbar("Deleted!") }
            },
            title = "Delete Record?",
            text = "This action is permanent and cannot be undone. All associated data will be lost."
        )
    }

    if (showBottomSheet) {
        AppModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .padding(PatFlowSpacing.space4)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
            ) {
                Text(
                    text = "Modal Bottom Sheet",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "This sheet follows the Design System §7.6 with XL rounded corners and a drag handle.",
                    style = MaterialTheme.typography.bodyMedium
                )
                AppButton(
                    onClick = { showBottomSheet = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close Sheet")
                }
                Spacer(modifier = Modifier.height(PatFlowSpacing.space4))
            }
        }
    }

    if (showDatePicker) {
        AppDatePickerDialog(
            onDateSelected = { date ->
                scope.launch { snackbarHostState.showSnackbar("Selected: $date") }
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Composable
private fun ShowcaseSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(modifier = Modifier.padding(bottom = PatFlowSpacing.space2))
        content()
    }
}
