package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.BillCategoryEntity
import com.patflow.app.data.local.entity.BillEntity
import kotlinx.coroutines.flow.Flow

/** Minimal CRUD for `bill` (the recurring template). Recurrence/cycle-generation logic lands with the Bills feature. */
@Dao
interface BillDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(bill: BillEntity): Long

    @Update
    suspend fun update(bill: BillEntity)

    @Delete
    suspend fun delete(bill: BillEntity)

    @Query("""
        SELECT * FROM bill 
        LEFT JOIN bill_category ON bill.category_id = bill_category.id
        WHERE bill.id = :id
    """)
    suspend fun getBillWithCategoryById(id: Long): Map<BillEntity, BillCategoryEntity>?

    @Query("""
        SELECT * FROM bill 
        JOIN bill_category ON bill.category_id = bill_category.id
        WHERE bill.is_deleted = 0 
        ORDER BY bill.name ASC
    """)
    fun getAllWithCategory(): Flow<Map<BillEntity, BillCategoryEntity>>

    @Query("SELECT * FROM bill WHERE category_id = :categoryId AND is_deleted = 0")
    fun getByCategory(categoryId: Long): Flow<List<BillEntity>>
}
