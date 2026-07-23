package com.patflow.app.domain.repository

import com.patflow.app.domain.model.Budget
import com.patflow.app.domain.model.BudgetLimit
import kotlinx.coroutines.flow.Flow

/**
 * Interface for budget-related data operations (Architecture §1.5 / Phase 10).
 */
interface BudgetRepository {

    fun getBudgets(): Flow<List<Budget>>
    
    suspend fun getBudgetById(id: Long): Budget?
    
    suspend fun insertBudget(budget: Budget): Long
    
    suspend fun updateBudget(budget: Budget)
    
    suspend fun deleteBudget(id: Long)
    
    suspend fun archiveBudget(id: Long, archived: Boolean)

    fun getCategoryLimits(budgetId: Long): Flow<List<BudgetLimit>>
    
    suspend fun insertCategoryLimit(limit: BudgetLimit): Long
    
    suspend fun deleteCategoryLimit(id: Long)
}
