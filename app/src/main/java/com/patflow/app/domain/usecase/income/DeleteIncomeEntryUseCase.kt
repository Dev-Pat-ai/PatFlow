package com.patflow.app.domain.usecase.income

import com.patflow.app.domain.repository.IncomeRepository
import javax.inject.Inject

/**
 * Use case for deleting a logged income entry (Architecture §1.12).
 */
class DeleteIncomeEntryUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteEntry(id)
}
