package com.patflow.app.data.repository

import com.patflow.app.data.local.dao.BillDao
import com.patflow.app.data.local.dao.BudgetDao
import com.patflow.app.data.local.dao.CategoryDao
import com.patflow.app.data.mapper.toDomain
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.Budget
import com.patflow.app.domain.model.BudgetLimit
import com.patflow.app.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao,
    private val categoryDao: CategoryDao
) : BudgetRepository {

    override fun getBudgets(): Flow<List<Budget>> {
        return budgetDao.getAllActive().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getBudgetById(id: Long): Budget? {
        return budgetDao.getById(id)?.toDomain()
    }

    override suspend fun insertBudget(budget: Budget): Long {
        return budgetDao.insert(budget.toEntity())
    }

    override suspend fun updateBudget(budget: Budget) {
        budgetDao.update(budget.toEntity())
    }

    override suspend fun deleteBudget(id: Long) {
        val budget = budgetDao.getById(id)
        budget?.let {
            budgetDao.update(it.copy(isDeleted = true))
        }
    }

    override suspend fun archiveBudget(id: Long, archived: Boolean) {
        val budget = budgetDao.getById(id)
        budget?.let {
            budgetDao.update(it.copy(isArchived = archived))
        }
    }

    override fun getCategoryLimits(budgetId: Long): Flow<List<BudgetLimit>> {
        return combine(
            budgetDao.getCategoryLimits(budgetId),
            categoryDao.getAll()
        ) { limitEntities, categories ->
            limitEntities.map { limit ->
                val category = categories.find { it.id == limit.categoryId }!!
                limit.toDomain(category.toDomain())
            }
        }
    }

    override suspend fun insertCategoryLimit(limit: BudgetLimit): Long {
        return budgetDao.insertCategoryLimit(limit.toEntity())
    }

    override suspend fun deleteCategoryLimit(id: Long) {
        // Find by ID and delete. DAO currently doesn't have getLimitById.
        // I'll add it or use a query.
    }
}
