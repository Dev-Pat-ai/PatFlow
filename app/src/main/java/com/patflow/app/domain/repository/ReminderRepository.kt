package com.patflow.app.domain.repository

import com.patflow.app.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

/**
 * Interface for managing persistent reminder records in the database (Architecture §Phase 8).
 */
interface ReminderRepository {
    
    fun getRemindersForCycle(cycleId: Long): Flow<List<Reminder>>
    
    suspend fun insertReminder(reminder: Reminder): Long
    
    suspend fun markAsSent(reminderId: Long)
    
    suspend fun deleteRemindersForCycle(cycleId: Long)

    /**
     * Schedules a WorkManager job to process due reminders.
     */
    fun scheduleReminderSync()
}
