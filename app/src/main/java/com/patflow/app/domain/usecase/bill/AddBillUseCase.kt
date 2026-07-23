package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.repository.BillRepository
import javax.inject.Inject

/**
 * Use case for adding a new bill to the system (Architecture §1.1).
 * Handles initial bill cycle generation via the repository.
 */
class AddBillUseCase @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(bill: Bill): Long {
        // Repository implementation will handle generating the initial cycle
        return repository.insertBill(bill)
    }
}
