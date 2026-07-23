package com.patflow.app.domain.usecase.budget

import com.patflow.app.domain.repository.BudgetRepository
import javax.inject.Inject

/**
 * Use case for archiving or unarchiving a budget (Architecture §1.5).
 */
class ArchiveBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(id: Long, archived: Boolean) = repository.archiveBudget(id, archived)
}
