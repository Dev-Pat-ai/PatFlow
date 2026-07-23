package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

/** Minimal CRUD for `reminder`. WorkManager scheduling logic lands with the Notifications feature. */
@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(reminder: ReminderEntity): Long

    @Update
    suspend fun update(reminder: ReminderEntity)

    @Delete
    suspend fun delete(reminder: ReminderEntity)

    @Query("SELECT * FROM reminder WHERE bill_cycle_id = :billCycleId")
    fun getByBillCycle(billCycleId: Long): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminder WHERE is_sent = 0 AND remind_at <= :nowIso")
    suspend fun getDuePending(nowIso: String): List<ReminderEntity>

    @Query("SELECT * FROM reminder WHERE id = :id")
    suspend fun getById(id: Long): ReminderEntity?

    @Query("DELETE FROM reminder WHERE bill_cycle_id = :cycleId")
    suspend fun deleteByCycle(cycleId: Long)
}
