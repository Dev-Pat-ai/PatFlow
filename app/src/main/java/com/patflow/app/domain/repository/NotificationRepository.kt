package com.patflow.app.domain.repository

import com.patflow.app.domain.model.NotificationType
import com.patflow.app.domain.model.SnoozeOption

/**
 * Interface for firing system notifications (Architecture §Phase 8).
 */
interface NotificationRepository {
    
    /**
     * Fires a notification of a specific type for a given bill cycle.
     */
    suspend fun showBillNotification(
        billCycleId: Long,
        type: NotificationType,
        title: String,
        message: String
    )

    /**
     * Fires a simple success/system notification.
     */
    suspend fun showSystemNotification(
        type: NotificationType,
        title: String,
        message: String
    )

    /**
     * Cancels an active notification.
     */
    fun cancelNotification(id: Int)
}
