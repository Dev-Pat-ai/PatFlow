package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching all active bills with their latest cycle information (Architecture §1.1).
 */
class GetBillsUseCase @Inject constructor(
    private val repository: BillRepository
) {
    operator fun invoke(): Flow<List<BillWithCycle>> = repository.getBillsWithCycles()
}
