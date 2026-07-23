package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * Room entity representing a logged payment against a bill cycle (Architecture §8.3 / FR-2.1).
 * Multiple payments can be associated with a single cycle (FR-2.2).
 */
@Entity(
    tableName = "payment",
    foreignKeys = [
        ForeignKey(
            entity = BillCycleEntity::class,
            parentColumns = ["id"],
            childColumns = ["bill_cycle_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["payment_date"]),
        Index(value = ["bill_cycle_id"]),
    ],
)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "bill_cycle_id")
    val billCycleId: Long,

    val amount: Double,

    @ColumnInfo(name = "payment_date")
    val paymentDate: LocalDate,

    /** PaymentMethod enum name — CASH / BANK_TRANSFER / EWALLET / CARD / OTHER. */
    val method: String,

    val note: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
)
