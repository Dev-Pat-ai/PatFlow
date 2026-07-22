package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

/** `reminder` — scheduled local notification for a bill cycle (Architecture §8.3 / FR-8.1). WorkManager query target. */
@Entity(
    tableName = "reminder",
    foreignKeys = [
        ForeignKey(
            entity = BillCycleEntity::class,
            parentColumns = ["id"],
            childColumns = ["bill_cycle_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["remind_at"])],
)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "bill_cycle_id")
    val billCycleId: Long,

    @ColumnInfo(name = "remind_at")
    val remindAt: LocalDateTime,

    @ColumnInfo(name = "is_sent", defaultValue = "0")
    val isSent: Boolean = false,

    @ColumnInfo(name = "offset_days")
    val offsetDays: Int,
)
