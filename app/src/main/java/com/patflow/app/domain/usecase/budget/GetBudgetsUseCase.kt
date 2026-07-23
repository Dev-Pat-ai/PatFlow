package com.patflow.app.domain.usecase.budget

import com.patflow.app.domain.model.Budget
import com.patflow.app.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching all active budgets (Architecture §1.5).
 */
class GetBudgetsUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(): Flow<List<Budget>> = repository.getBudgets()
}
