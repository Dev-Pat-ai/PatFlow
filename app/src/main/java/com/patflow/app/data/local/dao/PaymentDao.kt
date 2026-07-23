package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/** Minimal CRUD for `payment`. Cycle-status recalculation on insert/update/delete lands with the Bills feature. */
@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(payment: PaymentEntity): Long

    @Update
    suspend fun update(payment: PaymentEntity)

    @Delete
    suspend fun delete(payment: PaymentEntity)

    @Query("SELECT * FROM payment WHERE id = :id")
    suspend fun getById(id: Long): PaymentEntity?

    @Query("SELECT * FROM payment WHERE bill_cycle_id = :billCycleId ORDER BY payment_date DESC")
    fun getByBillCycle(billCycleId: Long): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM payment WHERE payment_date BETWEEN :start AND :end ORDER BY payment_date DESC")
    fun getByDateRange(start: String, end: String): Flow<List<PaymentEntity>>

    @Query("""
        SELECT 
            p.*, 
            b.name AS billName, 
            bc.id AS categoryId, bc.name AS categoryName, bc.icon_key AS categoryIcon, bc.color_hex AS categoryColor, bc.is_custom AS categoryIsCustom
        FROM payment p
        JOIN bill_cycle cycle ON p.bill_cycle_id = cycle.id
        JOIN bill b ON cycle.bill_id = b.id
        JOIN bill_category bc ON b.category_id = bc.id
        ORDER BY p.payment_date DESC
    """)
    fun getAllWithBillDetails(): Flow<List<PaymentWithBillDetails>>

    /**
     * Atomically deletes a payment and reverts the parent bill cycle balance and status.
     * Transactional to ensure data integrity during undo operations (FR-2.5).
     */
    @androidx.room.Transaction
    suspend fun deletePaymentAndAdjustCycle(paymentId: Long, cycleDao: BillCycleDao) {
        val payment = getById(paymentId) ?: return
        val cycle = cycleDao.getById(payment.billCycleId) ?: return
        
        // 1. Delete payment
        delete(payment)
        
        // 2. Adjust cycle
        val newPaid = (cycle.amountPaid - payment.amount).coerceAtLeast(0.0)
        val newStatus = if (newPaid >= cycle.amountDue) "PAID" else if (newPaid > 0) "PARTIALLY_PAID" else "UNPAID"
        
        cycleDao.update(cycle.copy(
            amountPaid = newPaid,
            status = newStatus,
            updatedAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
        ))
    }
}

data class PaymentWithBillDetails(
    @androidx.room.Embedded val payment: PaymentEntity,
    val billName: String,
    val categoryId: Long,
    val categoryName: String,
    val categoryIcon: String,
    val categoryColor: String,
    val categoryIsCustom: Boolean
)
