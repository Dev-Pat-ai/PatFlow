package com.patflow.app.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.patflow.app.core.components.BottomNavigationBar
import com.patflow.app.core.components.NavigationItem
import com.patflow.app.feature.bills.AddEditBillScreen
import com.patflow.app.feature.bills.BillDetailScreen
import com.patflow.app.feature.dashboard.DashboardScreen
import com.patflow.app.feature.income.AddEditIncomeScreen
import com.patflow.app.feature.income.AddEditIncomeSourceScreen
import com.patflow.app.feature.income.IncomeListScreen
import com.patflow.app.feature.income.IncomeSourceListScreen
import com.patflow.app.feature.money.MoneyScreen
import com.patflow.app.feature.payment.PaymentDetailScreen
import com.patflow.app.feature.payment.PaymentHistoryScreen
import com.patflow.app.feature.reports.ReportsScreen
import com.patflow.app.feature.settings.SettingsScreen
import com.patflow.app.feature.showcase.DesignSystemShowcaseScreen

/**
 * MainNavGraph shell (Architecture §6). Only the Dashboard destination has a
 * real placeholder composable — everything else is a stub screen so the app
 * boots to a navigable shell before feature screens exist. Each destination
 * gets a real screen as its feature is implemented.
 */
@Composable
fun PatFlowNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topLevelDestinations = listOf(
        Destinations.DASHBOARD,
        Destinations.MONEY,
        Destinations.CALENDAR,
        Destinations.REPORTS,
        Destinations.SETTINGS
    )

    val showBottomBar = currentRoute in topLevelDestinations

    val navItems = listOf(
        NavigationItem("Dashboard", Destinations.DASHBOARD, Icons.Rounded.Dashboard, Icons.Rounded.Dashboard),
        NavigationItem("Money", Destinations.MONEY, Icons.Rounded.AccountBalance, Icons.Rounded.AccountBalance),
        NavigationItem("Calendar", Destinations.CALENDAR, Icons.Rounded.CalendarMonth, Icons.Rounded.CalendarMonth),
        NavigationItem("Reports", Destinations.REPORTS, Icons.Rounded.PieChart, Icons.Rounded.PieChart),
        NavigationItem("Settings", Destinations.SETTINGS, Icons.Rounded.Settings, Icons.Rounded.Settings)
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    items = navItems,
                    selectedRoute = currentRoute ?: Destinations.DASHBOARD,
                    onItemClick = { item ->
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.DASHBOARD,
            modifier = Modifier.padding(padding)
        ) {
            composable(Destinations.DASHBOARD) { 
                DashboardScreen(
                    onBillClick = { billId ->
                        navController.navigate(Destinations.billDetail(billId))
                    },
                    onPaymentClick = { paymentId ->
                        navController.navigate(Destinations.paymentDetail(paymentId))
                    },
                    onAddBillClick = {
                        navController.navigate(Destinations.ADD_EDIT_BILL)
                    },
                    onLogPaymentClick = {
                        // For now, navigate to Money/Bills to use swipe or detail flow
                        // or future payment sheet.
                        navController.navigate(Destinations.MONEY)
                    }
                )
            }
            
            composable(Destinations.MONEY) { 
                MoneyScreen(
                    onBillClick = { billId ->
                        navController.navigate(Destinations.billDetail(billId))
                    },
                    onEditBillClick = { billId ->
                        navController.navigate("${Destinations.ADD_EDIT_BILL}?billId=$billId")
                    },
                    onAddBillClick = {
                        navController.navigate(Destinations.ADD_EDIT_BILL)
                    },
                    onAddIncomeClick = {
                        navController.navigate(Destinations.ADD_EDIT_INCOME)
                    },
                    onManageIncomeSourcesClick = {
                        navController.navigate(Destinations.INCOME_SOURCES)
                    },
                    onIncomeClick = { entryId ->
                        navController.navigate("${Destinations.ADD_EDIT_INCOME}?entryId=$entryId")
                    }
                )
            }

            composable(
                route = Destinations.BILL_DETAIL,
                arguments = listOf(navArgument("billId") { type = androidx.navigation.NavType.LongType })
            ) {
                BillDetailScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onEditClick = { billId ->
                        navController.navigate("${Destinations.ADD_EDIT_BILL}?billId=$billId")
                    }
                )
            }

            composable(
                route = "${Destinations.ADD_EDIT_BILL}?billId={billId}",
                arguments = listOf(navArgument("billId") { 
                    type = androidx.navigation.NavType.StringType
                    nullable = true
                    defaultValue = null 
                })
            ) {
                AddEditBillScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = "${Destinations.ADD_EDIT_INCOME}?entryId={entryId}",
                arguments = listOf(navArgument("entryId") { 
                    type = androidx.navigation.NavType.StringType
                    nullable = true
                    defaultValue = null 
                })
            ) {
                AddEditIncomeScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Destinations.INCOME_SOURCES) {
                IncomeSourceListScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onAddSourceClick = {
                        navController.navigate(Destinations.ADD_EDIT_INCOME_SOURCE)
                    },
                    onSourceClick = { sourceId ->
                        navController.navigate("${Destinations.ADD_EDIT_INCOME_SOURCE}?sourceId=$sourceId")
                    }
                )
            }

            composable(
                route = "${Destinations.ADD_EDIT_INCOME_SOURCE}?sourceId={sourceId}",
                arguments = listOf(navArgument("sourceId") { 
                    type = androidx.navigation.NavType.StringType
                    nullable = true
                    defaultValue = null 
                })
            ) {
                AddEditIncomeSourceScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Destinations.CALENDAR) { PlaceholderScreen("Calendar / Timeline") }
            composable(Destinations.REPORTS) { ReportsScreen() }
            composable(Destinations.SETTINGS) { 
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Destinations.PAYMENT_HISTORY) {
                PaymentHistoryScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onPaymentClick = { paymentId ->
                        navController.navigate(Destinations.paymentDetail(paymentId))
                    }
                )
            }

            composable(
                route = Destinations.PAYMENT_DETAIL,
                arguments = listOf(navArgument("paymentId") { type = androidx.navigation.NavType.LongType })
            ) {
                PaymentDetailScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(label: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "$label — coming in a later phase")
    }
}
