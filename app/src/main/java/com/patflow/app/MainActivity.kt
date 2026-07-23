package com.patflow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.patflow.app.core.navigation.PatFlowNavGraph
import com.patflow.app.core.notifications.NotificationScheduler
import com.patflow.app.core.theme.PatFlowTheme
import com.patflow.app.data.local.database.DatabaseInitializer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Single-Activity host (Architecture §6). Real start-destination gating
 * (onboarding vs. security_lock vs. dashboard) lands with the Onboarding
 * and Security features — this boots straight to the Dashboard placeholder
 * for now.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer

    @Inject
    lateinit var notificationScheduler: NotificationScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseInitializer.initialize()
        notificationScheduler.scheduleReminderSync()
        notificationScheduler.scheduleOverdueCheck()
        notificationScheduler.scheduleIncomeGeneration()
        enableEdgeToEdge()
        setContent {
            PatFlowTheme {
                PatFlowNavGraph()
            }
        }
    }
}
