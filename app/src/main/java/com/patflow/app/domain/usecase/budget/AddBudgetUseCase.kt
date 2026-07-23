package com.patflow.app.domain.usecase.budget

import com.patflow.app.domain.model.Budget
import com.patflow.app.domain.repository.BudgetRepository
import javax.inject.Inject

/**
 * Use case for creating a new budget (Architecture §1.5).
 */
class AddBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget): Long = repository.insertBudget(budget)
}
