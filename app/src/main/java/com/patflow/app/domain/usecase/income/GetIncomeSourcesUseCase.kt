package com.patflow.app.domain.usecase.income

import com.patflow.app.domain.model.IncomeSource
import com.patflow.app.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching all recurring income sources (Architecture §1.12).
 */
class GetIncomeSourcesUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    operator fun invoke(): Flow<List<IncomeSource>> = repository.getSources()
}
