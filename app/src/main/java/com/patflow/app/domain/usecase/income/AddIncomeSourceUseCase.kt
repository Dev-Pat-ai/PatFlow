package com.patflow.app.domain.usecase.income

import com.patflow.app.domain.model.IncomeSource
import com.patflow.app.domain.repository.IncomeRepository
import javax.inject.Inject

/**
 * Use case for creating or updating a recurring income source (Architecture §1.12).
 */
class AddIncomeSourceUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(source: IncomeSource): Long {
        return if (source.id == 0L) {
            repository.insertSource(source)
        } else {
            repository.updateSource(source)
            source.id
        }
    }
}
