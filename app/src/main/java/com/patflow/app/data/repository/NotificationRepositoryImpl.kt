package com.patflow.app.data.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.patflow.app.core.notifications.NotificationReceiver
import com.patflow.app.domain.model.NotificationType
import com.patflow.app.domain.repository.NotificationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [NotificationRepository] using Android System Notification service (Architecture §Phase 8).
 */
@Singleton
class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationRepository {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }

    override suspend fun showBillNotification(
        billCycleId: Long,
        type: NotificationType,
        title: String,
        message: String
    ) {
        val channelId = when (type) {
            NotificationType.OVERDUE_BILL -> CHANNEL_OVERDUE
            else -> CHANNEL_BILLS
        }

        val markPaidIntent = Intent(context, NotificationReceiver::class.java).apply {
            action = NotificationReceiver.ACTION_MARK_PAID
            putExtra(NotificationReceiver.EXTRA_CYCLE_ID, billCycleId)
        }
        val markPaidPendingIntent = PendingIntent.getBroadcast(
            context, billCycleId.toInt(), markPaidIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with app icon later
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(android.R.drawable.ic_menu_save, "Mark as Paid", markPaidPendingIntent)
            // Add Snooze action similarly

        notificationManager.notify(billCycleId.toInt(), builder.build())
    }

    override suspend fun showSystemNotification(
        type: NotificationType,
        title: String,
        message: String
    ) {
        val channelId = when (type) {
            NotificationType.PAYMENT_SUCCESS -> CHANNEL_PAYMENTS
            NotificationType.BACKUP_SUCCESS, NotificationType.RESTORE_SUCCESS -> CHANNEL_SYSTEM
            else -> CHANNEL_SYSTEM
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    override fun cancelNotification(id: Int) {
        notificationManager.cancel(id)
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(CHANNEL_BILLS, "Bills", NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "Reminders for upcoming and due bills"
                },
                NotificationChannel(CHANNEL_OVERDUE, "Overdue Alerts", NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "Urgent alerts for overdue payments"
                },
                NotificationChannel(CHANNEL_PAYMENTS, "Payments", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = "Confirmations for successful payments"
                },
                NotificationChannel(CHANNEL_SYSTEM, "System", NotificationManager.IMPORTANCE_LOW).apply {
                    description = "Backup, restore, and application updates"
                }
            )
            notificationManager.createNotificationChannels(channels)
        }
    }

    companion object {
        const val CHANNEL_BILLS = "bills_channel"
        const val CHANNEL_OVERDUE = "overdue_channel"
        const val CHANNEL_PAYMENTS = "payments_channel"
        const val CHANNEL_SYSTEM = "system_channel"
    }
}
