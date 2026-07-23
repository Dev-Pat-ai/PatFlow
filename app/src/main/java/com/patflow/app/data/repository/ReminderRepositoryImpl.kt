package com.patflow.app.data.repository

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.patflow.app.data.local.dao.ReminderDao
import com.patflow.app.data.mapper.toDomain
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.Reminder
import com.patflow.app.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Implementation of [ReminderRepository] using Room and WorkManager (Architecture §Phase 8).
 */
class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao,
    private val workManager: WorkManager
) : ReminderRepository {

    override fun getRemindersForCycle(cycleId: Long): Flow<List<Reminder>> {
        return reminderDao.getByBillCycle(cycleId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertReminder(reminder: Reminder): Long {
        return reminderDao.insert(reminder.toEntity())
    }

    override suspend fun markAsSent(reminderId: Long) {
        reminderDao.getById(reminderId)?.let {
            reminderDao.update(it.copy(isSent = true))
        }
    }

    override suspend fun deleteRemindersForCycle(cycleId: Long) {
        reminderDao.deleteByCycle(cycleId)
    }

    override suspend fun deleteRemindersForIncomeSource(sourceId: Long) {
        reminderDao.deleteByIncomeSource(sourceId)
    }

    override fun scheduleReminderSync() {
        // Implementation of WorkManager scheduling moves to a dedicated Scheduler class
        // but can be triggered from here or application start.
    }
}
