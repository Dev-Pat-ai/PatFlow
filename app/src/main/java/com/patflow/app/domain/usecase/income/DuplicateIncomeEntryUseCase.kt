package com.patflow.app.domain.usecase.income

import com.patflow.app.domain.repository.IncomeRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Use case for duplicating an existing income entry (Architecture §1.12).
 */
class DuplicateIncomeEntryUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(id: Long) {
        val detail = repository.getEntryById(id) ?: return
        val entry = detail.entry
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        
        repository.insertEntry(
            entry.copy(
                id = 0,
                createdAt = now
            )
        )
    }
}
