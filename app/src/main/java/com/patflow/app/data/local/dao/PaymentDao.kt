package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow

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
}
