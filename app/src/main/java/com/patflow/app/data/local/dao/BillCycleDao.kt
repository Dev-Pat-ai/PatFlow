package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.BillCycleEntity
import kotlinx.coroutines.flow.Flow

/** Minimal CRUD for `bill_cycle`. Status computation / next-cycle generation lands with the Bills feature. */
@Dao
interface BillCycleDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(cycle: BillCycleEntity): Long

    @Update
    suspend fun update(cycle: BillCycleEntity)

    @Delete
    suspend fun delete(cycle: BillCycleEntity)

    @Query("SELECT * FROM bill_cycle WHERE id = :id")
    suspend fun getById(id: Long): BillCycleEntity?

    @Query("SELECT * FROM bill_cycle WHERE bill_id = :billId ORDER BY due_date DESC")
    fun getByBill(billId: Long): Flow<List<BillCycleEntity>>

    @Query("SELECT * FROM bill_cycle WHERE due_date = :date")
    fun getByDueDate(date: String): Flow<List<BillCycleEntity>>

    @Query("SELECT * FROM bill_cycle WHERE status = :status")
    fun getByStatus(status: String): Flow<List<BillCycleEntity>>

    @Query("SELECT * FROM bill_cycle WHERE due_date BETWEEN :start AND :end ORDER BY due_date ASC")
    fun getByDateRange(start: String, end: String): Flow<List<BillCycleEntity>>

    @Query("""
        SELECT * FROM bill_cycle 
        WHERE status != 'PAID' 
        ORDER BY due_date ASC 
        LIMIT :limit
    """)
    fun getUpcoming(limit: Int): Flow<List<BillCycleEntity>>
}
