package com.patflow.app.domain.usecase.income

import com.patflow.app.domain.repository.IncomeRepository
import javax.inject.Inject

/**
 * Use case for archiving or unarchiving an income source (Architecture §1.12).
 */
class ArchiveIncomeSourceUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(id: Long, archived: Boolean) = repository.archiveSource(id, archived)
}
