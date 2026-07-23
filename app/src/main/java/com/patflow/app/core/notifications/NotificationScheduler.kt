package com.patflow.app.core.notifications

import android.content.Context
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Orchestrates WorkManager jobs for the notification system (Architecture §Phase 8).
 */
@Singleton
class NotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    /**
     * Schedules a periodic job to check for due reminders every 15 minutes (WorkManager minimum).
     */
    fun scheduleReminderSync() {
        val workRequest = PeriodicWorkRequestBuilder<ReminderSyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).build())
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_REMINDERS,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    /**
     * Schedules a daily job to check for overdue bill cycles.
     */
    fun scheduleOverdueCheck() {
        val workRequest = PeriodicWorkRequestBuilder<OverdueCheckWorker>(1, TimeUnit.DAYS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_OVERDUE,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    companion object {
        const val WORK_REMINDERS = "patflow_reminder_sync"
        const val WORK_OVERDUE = "patflow_overdue_check"
    }
}
