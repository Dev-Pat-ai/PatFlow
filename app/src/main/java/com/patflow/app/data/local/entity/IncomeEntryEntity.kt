package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * `income_entry` — the actual logged income; what dashboard/reports query
 * (Architecture §8.4 / FR-12.2, FR-12.3). `income_source_id` is nullable:
 * null = manual one-off entry. `category_id` is denormalized for fast
 * filtering even when `income_source_id` is null.
 */
@Entity(
    tableName = "income_entry",
    foreignKeys = [
        ForeignKey(
            entity = IncomeSourceEntity::class,
            parentColumns = ["id"],
            childColumns = ["income_source_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = IncomeCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
    indices = [
        Index(value = ["entry_date"]),
        Index(value = ["category_id"]),
        Index(value = ["income_source_id"]),
    ],
)
data class IncomeEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "income_source_id")
    val incomeSourceId: Long? = null, // null = manual one-off entry

    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    val amount: Double,

    @ColumnInfo(name = "currency_code", defaultValue = "PHP")
    val currencyCode: String = "PHP",

    @ColumnInfo(name = "entry_date")
    val entryDate: LocalDate,

    val note: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
)
