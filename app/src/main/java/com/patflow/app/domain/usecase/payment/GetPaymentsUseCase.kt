package com.patflow.app.domain.usecase.payment

import com.patflow.app.domain.model.PaymentHistory
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Logic to fetch all payments for history display.
 */
/**
 * Use case for fetching the complete transaction history (Architecture §1.2 / FR-2.4).
 */
class GetPaymentsUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    operator fun invoke(): Flow<List<PaymentHistory>> {
        return repository.getPayments()
    }
}
