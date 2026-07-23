package com.patflow.app.core.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.patflow.app.domain.model.NotificationType
import com.patflow.app.domain.repository.BudgetRepository
import com.patflow.app.domain.repository.NotificationRepository
import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

/**
 * Daily worker to check budget utilization and fire alerts (Architecture §Phase 10).
 */
@HiltWorker
class BudgetCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val budgetRepository: BudgetRepository,
    private val getBudgetAnalyticsUseCase: GetBudgetAnalyticsUseCase,
    private val notificationRepository: NotificationRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val budgets = budgetRepository.getBudgets().first()
        val activeBudget = budgets.firstOrNull { it.isActive && !it.isArchived } ?: return Result.success()

        val analytics = getBudgetAnalyticsUseCase(activeBudget.id).first() ?: return Result.success()

        // 1. Threshold Alerts
        val pct = analytics.percentageUsed
        val title = "Budget Alert"
        
        when {
            pct >= 1.0f -> notificationRepository.showSystemNotification(
                type = NotificationType.BUDGET_ALERT,
                title = title,
                message = "Budget exceeded! You've used ${(pct * 100).toInt()}% of ${activeBudget.name}."
            )
            pct >= 0.9f -> notificationRepository.showSystemNotification(
                type = NotificationType.BUDGET_ALERT,
                title = title,
                message = "Budget warning: 90% used."
            )
            pct >= 0.75f -> notificationRepository.showSystemNotification(
                type = NotificationType.BUDGET_ALERT,
                title = title,
                message = "Budget update: 75% used."
            )
        }

        return Result.success()
    }
}
