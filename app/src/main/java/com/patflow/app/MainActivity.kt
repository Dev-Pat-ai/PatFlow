package com.patflow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.patflow.app.core.navigation.PatFlowNavGraph
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseInitializer.initialize()
        enableEdgeToEdge()
        setContent {
            PatFlowTheme {
                PatFlowNavGraph()
            }
        }
    }
}
