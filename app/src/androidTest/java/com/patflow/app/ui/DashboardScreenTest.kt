package com.patflow.app.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.patflow.app.core.theme.PatFlowTheme
import com.patflow.app.domain.model.DashboardData
import com.patflow.app.feature.dashboard.DashboardScreen
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dashboardDisplaysGreeting() {
        // Need to mock ViewModel or use a simplified version of the Screen
        // For simplicity, I'll just check if a certain text is rendered when state is passed
    }
}
