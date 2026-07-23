package com.patflow.app.domain.usecase.savings

import com.patflow.app.domain.model.SavingsGoalAnalytics
import com.patflow.app.domain.repository.SavingsGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Aggregates goal data to compute progress, completion forecasts, and requirements (Architecture §Phase 11).
 */
class GetSavingsGoalAnalyticsUseCase @Inject constructor(
    private val repository: SavingsGoalRepository
) {
    operator fun invoke(goalId: Long): Flow<SavingsGoalAnalytics?> {
        return flow { emit(repository.getGoalById(goalId)) }.map { goal ->
            if (goal == null) return@map null

            val remaining = (goal.targetAmount - goal.currentAmount).coerceAtLeast(0.0)
            val progress = if (goal.targetAmount > 0) (goal.currentAmount / goal.targetAmount).toFloat() else 0f
            
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val targetDate = goal.targetDate
            
            val monthlyRequired = if (targetDate != null && targetDate > now) {
                val monthsRemaining = (now.daysUntil(targetDate) / 30.44).coerceAtLeast(1.0)
                remaining / monthsRemaining
            } else null

            SavingsGoalAnalytics(
                goal = goal,
                remainingAmount = remaining,
                progressPercentage = progress,
                estimatedCompletionDate = null, // Future: base on avg contribution rate
                monthlyRequiredSavings = monthlyRequired
            )
        }
    }
}
