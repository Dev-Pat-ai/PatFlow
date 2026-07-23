package com.patflow.app.domain.usecase.income

import com.patflow.app.domain.model.IncomeEntry
import com.patflow.app.domain.repository.IncomeRepository
import javax.inject.Inject

/**
 * Use case for logging a single income entry (Architecture §1.12).
 */
class AddIncomeEntryUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(entry: IncomeEntry): Long = repository.insertEntry(entry)
}
