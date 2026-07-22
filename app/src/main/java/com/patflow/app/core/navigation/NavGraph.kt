package com.patflow.app.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    NavHost(
        navController = navController,
        startDestination = Destinations.DASHBOARD,
    ) {
        composable(Destinations.DASHBOARD) { DesignSystemShowcaseScreen() }
        composable(Destinations.MONEY) { PlaceholderScreen("Money") }
        composable(Destinations.CALENDAR) { PlaceholderScreen("Calendar / Timeline") }
        composable(Destinations.REPORTS) { PlaceholderScreen("Reports") }
        composable(Destinations.SETTINGS) { PlaceholderScreen("Settings") }
    }
}

@Composable
private fun PlaceholderScreen(label: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "$label — coming in a later phase")
    }
}
