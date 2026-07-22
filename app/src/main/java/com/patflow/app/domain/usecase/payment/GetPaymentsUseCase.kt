package com.patflow.app.domain.usecase.payment

import com.patflow.app.domain.model.PaymentHistory
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Logic to fetch all payments for history display.
 */
class GetPaymentsUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    operator fun invoke(): Flow<List<PaymentHistory>> {
        return repository.getPayments()
    }
}
