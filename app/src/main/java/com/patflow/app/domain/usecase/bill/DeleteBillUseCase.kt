package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.repository.BillRepository
import javax.inject.Inject

class DeleteBillUseCase @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(billId: Long) {
        repository.deleteBill(billId)
    }
}
