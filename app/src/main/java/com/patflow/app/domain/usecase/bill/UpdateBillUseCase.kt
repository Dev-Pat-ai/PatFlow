package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.repository.BillRepository
import javax.inject.Inject

class UpdateBillUseCase @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(bill: Bill) {
        repository.updateBill(bill)
    }
}
