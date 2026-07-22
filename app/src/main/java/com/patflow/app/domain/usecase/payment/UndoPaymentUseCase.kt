package com.patflow.app.domain.usecase.payment

import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Logic to undo a payment and restore the bill cycle status.
 * Re-calculates status based on remaining payments.
 */
class UndoPaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val billRepository: BillRepository
) {
    suspend operator fun invoke(paymentId: Long) {
        // 1. Get payment and cycle info
        val history = paymentRepository.getPaymentById(paymentId).first() ?: return
        val payment = history.payment
        val cycle = billRepository.getCycleById(payment.billCycleId) ?: return
        
        // 2. Delete payment
        paymentRepository.deletePayment(paymentId)
        
        // 3. Recalculate cycle status
        val newAmountPaid = (cycle.amountPaid - payment.amount).coerceAtLeast(0.0)
        
        // Simplified status logic - in production, check due_date for OVERDUE
        val newStatus = when {
            newAmountPaid >= cycle.amountDue -> BillStatus.PAID
            newAmountPaid > 0 -> BillStatus.PARTIALLY_PAID
            else -> BillStatus.UNPAID
        }
        
        // Update bill cycle
        // Ideally we'd have a specific `updateCycle` in repository
        // For now, we reuse markCycleAsPaid with negative amount to decrement
        billRepository.markCycleAsPaid(cycle.id, -payment.amount, payment.method)
    }
}
