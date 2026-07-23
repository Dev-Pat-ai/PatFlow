package com.patflow.app.domain.usecase.savings

import com.patflow.app.domain.repository.SavingsGoalRepository
import javax.inject.Inject

/**
 * Use case for deleting a savings goal (Architecture §1.13).
 */
class DeleteSavingsGoalUseCase @Inject constructor(
    private val repository: SavingsGoalRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteGoal(id)
}
