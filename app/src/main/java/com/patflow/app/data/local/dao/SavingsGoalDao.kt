package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.patflow.app.data.local.entity.SavingsContributionEntity
import com.patflow.app.data.local.entity.SavingsGoalEntity
import kotlinx.coroutines.flow.Flow

/** Minimal CRUD for `savings_goal` and `savings_contribution`. Progress/completion recalculation lands with the Savings feature. */
@Dao
interface SavingsGoalDao {

    // ---- savings_goal ----
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertGoal(goal: SavingsGoalEntity): Long

    @Update
    suspend fun updateGoal(goal: SavingsGoalEntity)

    @Delete
    suspend fun deleteGoal(goal: SavingsGoalEntity)

    @Query("SELECT * FROM savings_goal WHERE id = :id")
    suspend fun getGoalById(id: Long): SavingsGoalEntity?

    @Query("SELECT * FROM savings_goal WHERE is_deleted = 0 ORDER BY created_at DESC")
    fun getAllGoals(): Flow<List<SavingsGoalEntity>>

    // ---- savings_contribution ----
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertContribution(contribution: SavingsContributionEntity): Long

    @Update
    suspend fun updateContribution(contribution: SavingsContributionEntity)

    @Delete
    suspend fun deleteContribution(contribution: SavingsContributionEntity)

    @Query("SELECT * FROM savings_contribution WHERE savings_goal_id = :goalId ORDER BY contribution_date DESC")
    fun getContributionsForGoal(goalId: Long): Flow<List<SavingsContributionEntity>>

    @Query("SELECT SUM(amount) FROM savings_contribution WHERE savings_goal_id = :goalId")
    suspend fun sumContributions(goalId: Long): Double?

    @Query("SELECT * FROM savings_contribution WHERE id = :id")
    suspend fun getContributionById(id: Long): SavingsContributionEntity?

    @Query("SELECT * FROM savings_goal")
    suspend fun getAllEntities(): List<SavingsGoalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(goals: List<SavingsGoalEntity>)

    @Query("DELETE FROM savings_goal")
    suspend fun deleteAll()

    @Query("SELECT * FROM savings_contribution")
    suspend fun getAllContributionEntities(): List<SavingsContributionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContributions(contributions: List<SavingsContributionEntity>)

    @Query("DELETE FROM savings_contribution")
    suspend fun deleteAllContributions()
}
