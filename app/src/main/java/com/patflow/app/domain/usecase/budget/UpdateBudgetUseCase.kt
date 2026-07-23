package com.patflow.app.domain.usecase.budget

import com.patflow.app.domain.model.Budget
import com.patflow.app.domain.repository.BudgetRepository
import javax.inject.Inject

/**
 * Use case for updating an existing budget (Architecture §1.5).
 */
class UpdateBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) = repository.updateBudget(budget)
}
