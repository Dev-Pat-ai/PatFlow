package com.patflow.app.domain.repository

import com.patflow.app.domain.model.SavingsContribution
import com.patflow.app.domain.model.SavingsGoal
import kotlinx.coroutines.flow.Flow

/**
 * Interface for savings-related data operations (Architecture §1.13 / Phase 11).
 */
interface SavingsGoalRepository {

    fun getGoals(): Flow<List<SavingsGoal>>
    
    suspend fun getGoalById(id: Long): SavingsGoal?
    
    suspend fun insertGoal(goal: SavingsGoal): Long
    
    suspend fun updateGoal(goal: SavingsGoal)
    
    suspend fun deleteGoal(id: Long)
    
    suspend fun archiveGoal(id: Long, archived: Boolean)
    
    suspend fun completeGoal(id: Long, completed: Boolean)

    fun getContributions(goalId: Long): Flow<List<SavingsContribution>>
    
    suspend fun insertContribution(contribution: SavingsContribution): Long
    
    suspend fun deleteContribution(id: Long)
}
