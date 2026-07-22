package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * `bill` — the recurring TEMPLATE (Architecture §8.1, §8.3). Separate from
 * `bill_cycle` (the per-period instance) — the single most important
 * modeling decision in the schema.
 */
@Entity(
    tableName = "bill",
    foreignKeys = [
        ForeignKey(
            entity = BillCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT, // prevent deleting a category in use; UI reassigns/soft-deletes instead
        ),
    ],
    indices = [
        Index(value = ["category_id"]),
        Index(value = ["merchant"]),
    ],
)
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    val name: String,

    @ColumnInfo(name = "default_amount")
    val defaultAmount: Double,

    @ColumnInfo(name = "currency_code", defaultValue = "PHP")
    val currencyCode: String = "PHP",

    /** e.g. "Meralco", "PLDT", landlord's name — search target (FR-17.1). */
    val merchant: String? = null,

    @ColumnInfo(name = "recurrence_type")
    val recurrenceType: String, // RecurrenceType enum name

    @ColumnInfo(name = "recurrence_interval", defaultValue = "1")
    val recurrenceInterval: Int = 1,

    @ColumnInfo(name = "due_day")
    val dueDay: Int? = null,

    @ColumnInfo(name = "start_date")
    val startDate: LocalDate,

    @ColumnInfo(name = "end_date")
    val endDate: LocalDate? = null,

    @ColumnInfo(name = "is_active", defaultValue = "1")
    val isActive: Boolean = true,

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    val isFavorite: Boolean = false,

    val notes: String? = null,

    @ColumnInfo(name = "is_deleted", defaultValue = "0")
    val isDeleted: Boolean = false,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,

    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime,

    // --- reserved for Installment Bills (Architecture §8.6, not implemented in v1) ---
    @ColumnInfo(name = "is_installment", defaultValue = "0")
    val isInstallment: Boolean = false,

    @ColumnInfo(name = "total_installments")
    val totalInstallments: Int? = null,

    // --- v2 sync scaffold ---
    @ColumnInfo(name = "remote_id")
    val remoteId: String? = null,

    @ColumnInfo(name = "last_synced_at")
    val lastSyncedAt: LocalDateTime? = null,

    @ColumnInfo(name = "sync_status", defaultValue = "LOCAL_ONLY")
    val syncStatus: String = "LOCAL_ONLY",

    @ColumnInfo(name = "is_dirty", defaultValue = "0")
    val isDirty: Boolean = false,
)
