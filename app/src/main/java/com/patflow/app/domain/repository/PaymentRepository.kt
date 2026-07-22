package com.patflow.app.domain.repository

import com.patflow.app.domain.model.Payment
import com.patflow.app.domain.model.PaymentHistory
import kotlinx.coroutines.flow.Flow

/**
 * Interface for payment-related data operations (Architecture §8.3).
 */
interface PaymentRepository {
    fun getPayments(): Flow<List<PaymentHistory>>
    fun getPaymentById(id: Long): Flow<PaymentHistory?>
    fun getPaymentsByCycle(cycleId: Long): Flow<List<Payment>>
    
    suspend fun insertPayment(payment: Payment): Long
    suspend fun deletePayment(id: Long)
}
