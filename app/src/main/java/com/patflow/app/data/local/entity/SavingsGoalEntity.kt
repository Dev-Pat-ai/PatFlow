package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * `savings_goal` — target-and-deadline-oriented goal, distinct from the
 * recurring "Savings" bill category (Architecture §1.13 / FR-13.1).
 */
@Entity(tableName = "savings_goal")
data class SavingsGoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    @ColumnInfo(name = "target_amount")
    val targetAmount: Double,

    @ColumnInfo(name = "currency_code", defaultValue = "PHP")
    val currencyCode: String = "PHP",

    @ColumnInfo(name = "current_amount", defaultValue = "0")
    val currentAmount: Double = 0.0, // denormalized sum of contributions

    @ColumnInfo(name = "target_date")
    val targetDate: LocalDate? = null,

    @ColumnInfo(name = "icon_key")
    val iconKey: String,

    @ColumnInfo(name = "color_hex")
    val colorHex: String,

    val notes: String? = null,

    val priority: Int = 0, // 0: Normal, 1: High

    @ColumnInfo(name = "is_completed", defaultValue = "0")
    val isCompleted: Boolean = false,

    @ColumnInfo(name = "is_archived", defaultValue = "0")
    val isArchived: Boolean = false,

    @ColumnInfo(name = "is_deleted", defaultValue = "0")
    val isDeleted: Boolean = false,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
)
