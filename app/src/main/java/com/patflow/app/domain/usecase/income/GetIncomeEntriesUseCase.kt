package com.patflow.app.domain.usecase.income

import com.patflow.app.domain.model.IncomeWithDetails
import com.patflow.app.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching all logged income entries (Architecture §1.12).
 */
class GetIncomeEntriesUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    operator fun invoke(): Flow<List<IncomeWithDetails>> = repository.getEntries()
}
