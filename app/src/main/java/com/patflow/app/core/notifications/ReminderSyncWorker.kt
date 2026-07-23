package com.patflow.app.core.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.patflow.app.data.local.dao.ReminderDao
import com.patflow.app.data.local.entity.ReminderEntity
import com.patflow.app.domain.model.NotificationType
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.IncomeRepository
import com.patflow.app.domain.repository.NotificationRepository
import com.patflow.app.domain.repository.ReminderRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Periodically checks for due reminders and fires system notifications (Architecture §Phase 8).
 */
@HiltWorker
class ReminderSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val reminderDao: ReminderDao,
    private val reminderRepository: ReminderRepository,
    private val billRepository: BillRepository,
    private val incomeRepository: IncomeRepository,
    private val notificationRepository: NotificationRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val dueReminders = reminderDao.getDuePending(now.toString())

        for (reminder in dueReminders) {
            if (reminder.billCycleId != null) {
                processBillReminder(reminder)
            } else if (reminder.incomeSourceId != null) {
                processIncomeReminder(reminder)
            }
            
            reminderRepository.markAsSent(reminder.id)
        }

        return Result.success()
    }

    private suspend fun processBillReminder(reminder: ReminderEntity) {
        val cycle = billRepository.getCycleById(reminder.billCycleId!!) ?: return
        val bill = billRepository.getBillById(cycle.billId).first() ?: return

        val title = when (reminder.offsetDays) {
            0 -> "Bill Due Today"
            else -> "Upcoming Bill"
        }
        val message = "${bill.name} is due on ${cycle.dueDate} (₱${cycle.amountDue})"

        notificationRepository.showBillNotification(
            billCycleId = cycle.id,
            type = if (reminder.offsetDays == 0) NotificationType.DUE_TODAY else NotificationType.UPCOMING_BILL,
            title = title,
            message = message
        )
    }

    private suspend fun processIncomeReminder(reminder: ReminderEntity) {
        val source = incomeRepository.getSourceById(reminder.incomeSourceId!!) ?: return

        val title = "Income Expected"
        val message = "${source.name} is expected tomorrow (₱${source.defaultAmount})"

        notificationRepository.showSystemNotification(
            type = NotificationType.UPCOMING_BILL, // Reuse or add INCOME type
            title = title,
            message = message
        )
    }
}
