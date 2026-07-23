package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.repository.BillRepository
import javax.inject.Inject

/**
 * Use case for deleting a bill (Architecture §1.1 / FR-1.2).
 * Performs a soft delete to preserve historical payment data.
 */
class DeleteBillUseCase @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(billId: Long) {
        repository.deleteBill(billId)
    }
}
