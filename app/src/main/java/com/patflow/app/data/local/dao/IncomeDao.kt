package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.IncomeCategoryEntity
import com.patflow.app.data.local.entity.IncomeEntryEntity
import com.patflow.app.data.local.entity.IncomeSourceEntity
import kotlinx.coroutines.flow.Flow

/** Minimal CRUD for the income tables (`income_category`, `income_source`, `income_entry`). */
@Dao
interface IncomeDao {

    // ---- income_category ----
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategory(category: IncomeCategoryEntity): Long

    @Update
    suspend fun updateCategory(category: IncomeCategoryEntity)

    @Delete
    suspend fun deleteCategory(category: IncomeCategoryEntity)

    @Query("SELECT * FROM income_category ORDER BY name ASC")
    fun getAllCategories(): Flow<List<IncomeCategoryEntity>>

    // ---- income_source (recurring template, optional) ----
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSource(source: IncomeSourceEntity): Long

    @Update
    suspend fun updateSource(source: IncomeSourceEntity)

    @Delete
    suspend fun deleteSource(source: IncomeSourceEntity)

    @Query("SELECT * FROM income_source WHERE id = :id")
    suspend fun getSourceById(id: Long): IncomeSourceEntity?

    @Query("SELECT * FROM income_source WHERE is_deleted = 0 ORDER BY name ASC")
    fun getAllSources(): Flow<List<IncomeSourceEntity>>

    // ---- income_entry (the actual logged income) ----
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEntry(entry: IncomeEntryEntity): Long

    @Update
    suspend fun updateEntry(entry: IncomeEntryEntity)

    @Delete
    suspend fun deleteEntry(entry: IncomeEntryEntity)

    @Query("SELECT * FROM income_entry WHERE id = :id")
    suspend fun getEntryById(id: Long): IncomeEntryEntity?

    @Query("SELECT * FROM income_entry ORDER BY entry_date DESC")
    fun getAllEntries(): Flow<List<IncomeEntryEntity>>

    @Query("SELECT * FROM income_entry WHERE entry_date BETWEEN :start AND :end ORDER BY entry_date DESC")
    fun getEntriesByDateRange(start: String, end: String): Flow<List<IncomeEntryEntity>>

    @Query("SELECT * FROM income_entry WHERE category_id = :categoryId ORDER BY entry_date DESC")
    fun getEntriesByCategory(categoryId: Long): Flow<List<IncomeEntryEntity>>

    @Query("SELECT * FROM income_category")
    suspend fun getAllCategoryEntities(): List<IncomeCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<IncomeCategoryEntity>)

    @Query("DELETE FROM income_category")
    suspend fun deleteAllCategories()

    @Query("SELECT * FROM income_source")
    suspend fun getAllSourceEntities(): List<IncomeSourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSources(sources: List<IncomeSourceEntity>)

    @Query("DELETE FROM income_source")
    suspend fun deleteAllSources()

    @Query("SELECT * FROM income_entry")
    suspend fun getAllEntryEntities(): List<IncomeEntryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEntries(entries: List<IncomeEntryEntity>)

    @Query("DELETE FROM income_entry")
    suspend fun deleteAllEntries()
}
