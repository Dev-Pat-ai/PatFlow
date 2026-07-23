package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.model.Payment
import com.patflow.app.domain.model.PaymentMethod
import com.patflow.app.domain.usecase.payment.LogPaymentUseCase
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Use case for marking a specific bill cycle as paid (Architecture §1.2 / FR-2.1).
 * Orchestrates the creation of a [Payment] record and updates the [BillCycle] status.
 */
class MarkBillAsPaidUseCase @Inject constructor(
    private val logPaymentUseCase: LogPaymentUseCase
) {
    suspend operator fun invoke(
        cycleId: Long,
        amount: Double,
        method: PaymentMethod = PaymentMethod.OTHER
    ) {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val payment = Payment(
            billCycleId = cycleId,
            amount = amount,
            paymentDate = now.date,
            method = method,
            createdAt = now
        )
        logPaymentUseCase(payment)
    }
}
