package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.repository.BillRepository
import javax.inject.Inject

class AddBillUseCase @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(bill: Bill): Long {
        // Repository implementation will handle generating the initial cycle
        return repository.insertBill(bill)
    }
}
