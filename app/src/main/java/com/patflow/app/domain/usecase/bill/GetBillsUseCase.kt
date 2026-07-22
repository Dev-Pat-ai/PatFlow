package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBillsUseCase @Inject constructor(
    private val repository: BillRepository
) {
    operator fun invoke(): Flow<List<BillWithCycle>> = repository.getBillsWithCycles()
}
