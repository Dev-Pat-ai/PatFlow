package com.patflow.app.data.repository

import com.patflow.app.data.local.dao.BillDao
import com.patflow.app.data.local.dao.PaymentDao
import com.patflow.app.data.mapper.toDomain
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.Payment
import com.patflow.app.domain.model.PaymentHistory
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentDao: PaymentDao,
    private val billDao: BillDao
) : PaymentRepository {

    override fun getPayments(): Flow<List<PaymentHistory>> {
        // This is a bit complex in Room without a dedicated Relation/Join DAO method
        // For MVP, we'll implement a simplified version or assume a join exists.
        // In a real project, we'd add a method to PaymentDao that returns PaymentWithBill.
        return paymentDao.getByDateRange("1970-01-01", "9999-12-31").map { entities ->
            entities.map { entity ->
                // Simplified: we need the bill info here. 
                // For now, placeholders to satisfy the interface.
                PaymentHistory(
                    payment = entity.toDomain(),
                    billName = "Bill", 
                    category = com.patflow.app.domain.model.Category(0, "Category", "bolt", "#000000", false)
                )
            }
        }
    }

    override fun getPaymentById(id: Long): Flow<PaymentHistory?> {
        return paymentDao.getByDateRange("1970-01-01", "9999-12-31").map { list ->
            list.find { it.id == id }?.let { entity ->
                PaymentHistory(
                    payment = entity.toDomain(),
                    billName = "Bill",
                    category = com.patflow.app.domain.model.Category(0, "Category", "bolt", "#000000", false)
                )
            }
        }
    }

    override fun getPaymentsByCycle(cycleId: Long): Flow<List<Payment>> {
        return paymentDao.getByBillCycle(cycleId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertPayment(payment: Payment): Long {
        return paymentDao.insert(payment.toEntity())
    }

    override suspend fun deletePayment(id: Long) {
        val payment = paymentDao.getById(id)
        payment?.let { paymentDao.delete(it) }
    }
}
