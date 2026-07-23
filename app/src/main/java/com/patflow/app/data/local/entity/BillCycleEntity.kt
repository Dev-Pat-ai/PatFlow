package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * Room entity representing a single billing period instance (Architecture §8.3).
 * Tracks actual due amount, paid amount, and derived status for a specific period.
 */
@Entity(
    tableName = "bill_cycle",
    foreignKeys = [
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = ["id"],
            childColumns = ["bill_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["due_date"]),
        Index(value = ["status"]),
        Index(value = ["bill_id", "due_date"]),
    ],
)
data class BillCycleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "bill_id")
    val billId: Long,

    @ColumnInfo(name = "period_start")
    val periodStart: LocalDate,

    @ColumnInfo(name = "due_date")
    val dueDate: LocalDate,

    @ColumnInfo(name = "amount_due")
    val amountDue: Double,

    @ColumnInfo(name = "amount_paid", defaultValue = "0")
    val amountPaid: Double = 0.0,

    /** BillCycleStatus enum name — UNPAID / PARTIALLY_PAID / PAID / OVERDUE. Derived, never user-typed. */
    val status: String,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,

    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime,

    // --- reserved for Installment Bills (Architecture §8.6, not implemented in v1) ---
    @ColumnInfo(name = "installment_number")
    val installmentNumber: Int? = null,
)
