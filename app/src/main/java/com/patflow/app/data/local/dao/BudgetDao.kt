package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.BudgetCategoryLimitEntity
import com.patflow.app.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

/** Minimal CRUD for `budget` and `budget_category_limit`. */
@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEntity): Long

    @Update
    suspend fun update(budget: BudgetEntity)

    @Delete
    suspend fun delete(budget: BudgetEntity)

    @Query("SELECT * FROM budget WHERE month = :month AND year = :year")
    suspend fun getForPeriod(month: Int, year: Int): BudgetEntity?

    @Query("SELECT * FROM budget ORDER BY year DESC, month DESC")
    fun getAll(): Flow<List<BudgetEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategoryLimit(limit: BudgetCategoryLimitEntity): Long

    @Update
    suspend fun updateCategoryLimit(limit: BudgetCategoryLimitEntity)

    @Delete
    suspend fun deleteCategoryLimit(limit: BudgetCategoryLimitEntity)

    @Query("SELECT * FROM budget_category_limit WHERE budget_id = :budgetId")
    fun getCategoryLimits(budgetId: Long): Flow<List<BudgetCategoryLimitEntity>>
}
