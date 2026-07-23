package com.patflow.app.domain.usecase.savings

import com.patflow.app.domain.model.SavingsGoal
import com.patflow.app.domain.repository.SavingsGoalRepository
import javax.inject.Inject

/**
 * Use case for updating an existing savings goal (Architecture §1.13).
 */
class UpdateSavingsGoalUseCase @Inject constructor(
    private val repository: SavingsGoalRepository
) {
    suspend operator fun invoke(goal: SavingsGoal) = repository.updateGoal(goal)
}
