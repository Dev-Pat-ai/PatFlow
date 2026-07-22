package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.model.PaymentMethod
import com.patflow.app.domain.repository.BillRepository
import javax.inject.Inject

class MarkBillAsPaidUseCase @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(
        cycleId: Long,
        amount: Double,
        method: PaymentMethod = PaymentMethod.OTHER
    ) {
        repository.markCycleAsPaid(cycleId, amount, method)
    }
}
