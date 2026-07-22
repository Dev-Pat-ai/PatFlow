package com.patflow.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** Hilt entry point — enables @AndroidEntryPoint / @Inject throughout the app. */
@HiltAndroidApp
class PatFlowApplication : Application()
