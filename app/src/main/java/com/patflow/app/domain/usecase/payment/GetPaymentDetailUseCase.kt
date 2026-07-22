package com.patflow.app.domain.usecase.payment

import com.patflow.app.domain.model.PaymentHistory
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Logic to fetch a single payment's details.
 */
class GetPaymentDetailUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    operator fun invoke(paymentId: Long): Flow<PaymentHistory?> {
        return repository.getPaymentById(paymentId)
    }
}
