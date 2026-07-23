package com.patflow.app.core.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.patflow.app.domain.model.Reminder
import com.patflow.app.domain.repository.ReminderRepository
import com.patflow.app.domain.usecase.bill.MarkBillAsPaidUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

/**
 * Handles background actions from notifications (Snooze, Mark as Paid) (Architecture §Phase 8).
 */
@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var markBillAsPaidUseCase: MarkBillAsPaidUseCase

    @Inject
    lateinit var reminderRepository: ReminderRepository

    override fun onReceive(context: Context, intent: Intent) {
        val cycleId = intent.getLongExtra(EXTRA_CYCLE_ID, -1)
        if (cycleId == -1L) return

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

        when (intent.action) {
            ACTION_MARK_PAID -> {
                val amount = intent.getDoubleExtra(EXTRA_AMOUNT, 0.0)
                CoroutineScope(Dispatchers.IO).launch {
                    markBillAsPaidUseCase(cycleId, amount)
                }
                notificationManager.cancel(cycleId.toInt())
            }
            ACTION_SNOOZE -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val snoozeTime = Clock.System.now().plus(1.hours).toLocalDateTime(TimeZone.currentSystemDefault())
                    reminderRepository.insertReminder(
                        Reminder(
                            billCycleId = cycleId,
                            remindAt = snoozeTime,
                            offsetDays = 0 // Manual snooze
                        )
                    )
                }
                notificationManager.cancel(cycleId.toInt())
            }
        }
    }

    companion object {
        const val ACTION_MARK_PAID = "com.patflow.app.ACTION_MARK_PAID"
        const val ACTION_SNOOZE = "com.patflow.app.ACTION_SNOOZE"
        const val EXTRA_CYCLE_ID = "extra_cycle_id"
        const val EXTRA_AMOUNT = "extra_amount"
    }
}
