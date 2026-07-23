package com.patflow.app.domain.usecase.budget

import com.patflow.app.domain.repository.BudgetRepository
import javax.inject.Inject

/**
 * Use case for deleting a budget (Architecture §1.5).
 */
class DeleteBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteBudget(id)
}
