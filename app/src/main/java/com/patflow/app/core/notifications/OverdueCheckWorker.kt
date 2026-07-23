package com.patflow.app.core.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.NotificationType
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.NotificationRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Periodically checks for overdue bill cycles and alerts the user (Architecture §Phase 8).
 */
@HiltWorker
class OverdueCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val billRepository: BillRepository,
    private val notificationRepository: NotificationRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        
        // We need a way to get all unpaid cycles across all bills
        // For MVP, we use getUpcomingCycles(100) or similar, or add a specific query
        val unpaidCycles = billRepository.getUpcomingCycles(100).first()
        
        for (cycle in unpaidCycles) {
            if (cycle.dueDate < now && cycle.status != BillStatus.PAID) {
                val bill = billRepository.getBillById(cycle.billId).first() ?: continue
                
                notificationRepository.showBillNotification(
                    billCycleId = cycle.id,
                    type = NotificationType.OVERDUE_BILL,
                    title = "Bill Overdue",
                    message = "${bill.name} was due on ${cycle.dueDate} (₱${cycle.amountDue})"
                )
            }
        }

        return Result.success()
    }
}
