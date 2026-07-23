package com.patflow.app.data.repository

import com.patflow.app.data.local.dao.SavingsGoalDao
import com.patflow.app.data.mapper.toDomain
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.SavingsContribution
import com.patflow.app.domain.model.SavingsGoal
import com.patflow.app.domain.repository.SavingsGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavingsGoalRepositoryImpl @Inject constructor(
    private val savingsGoalDao: SavingsGoalDao
) : SavingsGoalRepository {

    override fun getGoals(): Flow<List<SavingsGoal>> =
        savingsGoalDao.getAllGoals().map { entities -> 
            entities.map { it.toDomain() } 
        }

    override suspend fun getGoalById(id: Long): SavingsGoal? =
        savingsGoalDao.getGoalById(id)?.toDomain()

    override suspend fun insertGoal(goal: SavingsGoal): Long =
        savingsGoalDao.insertGoal(goal.toEntity())

    override suspend fun updateGoal(goal: SavingsGoal) =
        savingsGoalDao.updateGoal(goal.toEntity())

    override suspend fun deleteGoal(id: Long) {
        savingsGoalDao.getGoalById(id)?.let {
            savingsGoalDao.updateGoal(it.copy(isDeleted = true))
        }
    }

    override suspend fun archiveGoal(id: Long, archived: Boolean) {
        savingsGoalDao.getGoalById(id)?.let {
            savingsGoalDao.updateGoal(it.copy(isArchived = archived))
        }
    }

    override suspend fun completeGoal(id: Long, completed: Boolean) {
        savingsGoalDao.getGoalById(id)?.let {
            savingsGoalDao.updateGoal(it.copy(isCompleted = completed))
        }
    }

    override fun getContributions(goalId: Long): Flow<List<SavingsContribution>> =
        savingsGoalDao.getContributionsForGoal(goalId).map { entities -> 
            entities.map { it.toDomain() } 
        }

    override suspend fun insertContribution(contribution: SavingsContribution): Long {
        val id = savingsGoalDao.insertContribution(contribution.toEntity())
        updateGoalAmount(contribution.savingsGoalId)
        return id
    }

    override suspend fun deleteContribution(id: Long) {
        val contribution = savingsGoalDao.getContributionById(id) ?: return
        savingsGoalDao.deleteContribution(contribution)
        updateGoalAmount(contribution.savingsGoalId)
    }

    private suspend fun updateGoalAmount(goalId: Long) {
        val sum = savingsGoalDao.sumContributions(goalId) ?: 0.0
        val goal = savingsGoalDao.getGoalById(goalId) ?: return
        savingsGoalDao.updateGoal(goal.copy(
            currentAmount = sum,
            isCompleted = sum >= goal.targetAmount
        ))
    }
}
