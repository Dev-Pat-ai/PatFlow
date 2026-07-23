package com.patflow.app.data.repository

import com.patflow.app.data.local.dao.BillCycleDao
import com.patflow.app.data.local.dao.PaymentDao
import com.patflow.app.data.mapper.toDomain
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.Payment
import com.patflow.app.domain.model.PaymentHistory
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [PaymentRepository] using Room DAOs (Architecture §9).
 * Orchestrates transactions and JOIN-based historical queries.
 */
class PaymentRepositoryImpl @Inject constructor(
    private val paymentDao: PaymentDao,
    private val billCycleDao: BillCycleDao
) : PaymentRepository {

    override fun getPayments(): Flow<List<PaymentHistory>> {
        return paymentDao.getAllWithBillDetails().map { list ->
            list.map { detail ->
                PaymentHistory(
                    payment = detail.payment.toDomain(),
                    billName = detail.billName,
                    category = com.patflow.app.domain.model.Category(
                        id = detail.categoryId,
                        name = detail.categoryName,
                        iconKey = detail.categoryIcon,
                        colorHex = detail.categoryColor,
                        isCustom = detail.categoryIsCustom
                    )
                )
            }
        }
    }

    override fun getPaymentById(id: Long): Flow<PaymentHistory?> {
        return paymentDao.getAllWithBillDetails().map { list ->
            list.find { it.payment.id == id }?.let { detail ->
                PaymentHistory(
                    payment = detail.payment.toDomain(),
                    billName = detail.billName,
                    category = com.patflow.app.domain.model.Category(
                        id = detail.categoryId,
                        name = detail.categoryName,
                        iconKey = detail.categoryIcon,
                        colorHex = detail.categoryColor,
                        isCustom = detail.categoryIsCustom
                    )
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

    override suspend fun undoPayment(paymentId: Long) {
        paymentDao.deletePaymentAndAdjustCycle(paymentId, billCycleDao)
    }
}
