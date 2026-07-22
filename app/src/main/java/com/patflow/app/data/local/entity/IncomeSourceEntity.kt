package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

/**
 * `income_source` — recurring income TEMPLATE, optional (Architecture §8.4 / FR-12.2).
 * One-off income skips this and writes directly to `income_entry`.
 * Deliberately mirrors the bill template/instance pattern.
 */
@Entity(
    tableName = "income_source",
    foreignKeys = [
        ForeignKey(
            entity = IncomeCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
    indices = [Index(value = ["category_id"])],
)
data class IncomeSourceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    val name: String, // e.g. "Monthly Salary"

    @ColumnInfo(name = "default_amount")
    val defaultAmount: Double,

    @ColumnInfo(name = "recurrence_type")
    val recurrenceType: String, // same enum as bill.recurrence_type

    @ColumnInfo(name = "recurrence_interval", defaultValue = "1")
    val recurrenceInterval: Int = 1,

    @ColumnInfo(name = "start_date")
    val startDate: LocalDate,

    @ColumnInfo(name = "end_date")
    val endDate: LocalDate? = null,

    @ColumnInfo(name = "is_active", defaultValue = "1")
    val isActive: Boolean = true,

    @ColumnInfo(name = "is_deleted", defaultValue = "0")
    val isDeleted: Boolean = false,
)
