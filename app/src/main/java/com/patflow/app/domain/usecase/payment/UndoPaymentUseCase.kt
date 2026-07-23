package com.patflow.app.domain.usecase.payment

import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Use case for reverting a logged payment (Architecture §1.2 / FR-2.5).
 * Physically removes the payment record and restores the bill cycle status.
 */
class UndoPaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(paymentId: Long) {
        paymentRepository.undoPayment(paymentId)
    }
}
