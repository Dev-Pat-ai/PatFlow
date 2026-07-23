package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.BillCategoryEntity
import kotlinx.coroutines.flow.Flow

/** Minimal CRUD for `bill_category`. Business logic (e.g. seeding the 11 predefined categories) lands with the Bills feature. */
@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(category: BillCategoryEntity): Long

    @Update
    suspend fun update(category: BillCategoryEntity)

    @Delete
    suspend fun delete(category: BillCategoryEntity)

    @Query("SELECT * FROM bill_category WHERE id = :id")
    suspend fun getById(id: Long): BillCategoryEntity?

    @Query("SELECT * FROM bill_category WHERE is_deleted = 0 ORDER BY name ASC")
    fun getAll(): Flow<List<BillCategoryEntity>>

    @Query("SELECT * FROM bill_category")
    suspend fun getAllEntities(): List<BillCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<BillCategoryEntity>)

    @Query("DELETE FROM bill_category")
    suspend fun deleteAll()
}
