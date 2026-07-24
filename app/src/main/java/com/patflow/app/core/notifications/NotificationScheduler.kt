package com.patflow.app.core.notifications

import android.content.Context
import android.util.Log
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
    /**
     * Schedules a periodic job to check for due reminders every 15 minutes (WorkManager minimum).
     */
    fun scheduleReminderSync() {
        val workRequest = PeriodicWorkRequestBuilder<ReminderSyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).build())
            .build()

        enqueueUniquePeriodicWork(
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

        enqueueUniquePeriodicWork(
            WORK_OVERDUE,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    /**
     * Schedules a daily job to check for recurring income templates.
     */
    fun scheduleIncomeGeneration() {
        val workRequest = PeriodicWorkRequestBuilder<RecurringIncomeWorker>(1, TimeUnit.DAYS)
            .build()

        enqueueUniquePeriodicWork(
            WORK_INCOME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    /**
     * Schedules a daily job to check budget thresholds.
     */
    fun scheduleBudgetCheck() {
        val workRequest = PeriodicWorkRequestBuilder<BudgetCheckWorker>(1, TimeUnit.DAYS)
            .build()

        enqueueUniquePeriodicWork(
            WORK_BUDGET,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun enqueueUniquePeriodicWork(
        uniqueWorkName: String,
        policy: ExistingPeriodicWorkPolicy,
        workRequest: PeriodicWorkRequest
    ) {
        runCatching {
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                uniqueWorkName,
                policy,
                workRequest
            )
        }.onFailure {
            Log.e(TAG, "Unable to schedule $uniqueWorkName", it)
        }
    }

    companion object {
        private const val TAG = "NotificationScheduler"
        const val WORK_REMINDERS = "patflow_reminder_sync"
        const val WORK_OVERDUE = "patflow_overdue_check"
        const val WORK_INCOME = "patflow_income_generation"
        const val WORK_BUDGET = "patflow_budget_check"
    }
}
