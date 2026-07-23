package com.patflow.app.core.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.patflow.app.domain.usecase.bill.MarkBillAsPaidUseCase
import com.patflow.app.domain.usecase.payment.UndoPaymentUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles background actions from notifications (Snooze, Mark as Paid) (Architecture §Phase 8).
 */
@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var markBillAsPaidUseCase: MarkBillAsPaidUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val cycleId = intent.getLongExtra(EXTRA_CYCLE_ID, -1)
        if (cycleId == -1L) return

        when (intent.action) {
            ACTION_MARK_PAID -> {
                val amount = intent.getDoubleExtra(EXTRA_AMOUNT, 0.0)
                CoroutineScope(Dispatchers.IO).launch {
                    markBillAsPaidUseCase(cycleId, amount)
                }
                // Cancel notification
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                notificationManager.cancel(cycleId.toInt())
            }
            ACTION_SNOOZE -> {
                // TODO: Implement snooze rescheduling logic
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
