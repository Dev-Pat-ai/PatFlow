package com.patflow.app.domain.usecase.savings

import com.patflow.app.domain.model.SavingsGoal
import com.patflow.app.domain.repository.SavingsGoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching all active savings goals (Architecture §1.13).
 */
class GetSavingsGoalsUseCase @Inject constructor(
    private val repository: SavingsGoalRepository
) {
    operator fun invoke(): Flow<List<SavingsGoal>> = repository.getGoals()
}
