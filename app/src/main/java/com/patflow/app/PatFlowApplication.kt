package com.patflow.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for PatFlow.
 * Entry point for Hilt dependency injection initialization.
 */
@HiltAndroidApp
class PatFlowApplication : Application()
