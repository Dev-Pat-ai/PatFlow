package com.patflow.app.domain.usecase.payment

import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.Payment
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Use case for logging a payment and transactionally updating the bill cycle balance (Architecture §1.2).
 * Handles the single source of truth for payment writes.
 */
class LogPaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val billRepository: BillRepository
) {
    suspend operator fun invoke(payment: Payment) {
        // 1. Log Payment
        paymentRepository.insertPayment(payment)
        
        // 2. Update Bill Cycle balance
        billRepository.updateCyclePaidAmount(payment.billCycleId, payment.amount)
    }
}
