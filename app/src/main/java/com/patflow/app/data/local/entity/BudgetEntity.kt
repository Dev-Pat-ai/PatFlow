package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * `budget` — total monthly budget (Architecture §8.3 / FR-5.1, FR-5.2).
 *
 * NOTE (Phase 0 review — logged as a future implementation consideration,
 * not a blocker): unlike bill/income/goal, this table has no `currency_code`.
 * total_amount is implicitly in the user's default currency; multi-currency
 * budget math is undefined and deferred.
 */
@Entity(
    tableName = "budget",
    indices = [Index(value = ["month", "year"], unique = true)],
)
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val month: Int, // 1-12

    val year: Int,

    @ColumnInfo(name = "total_amount")
    val totalAmount: Double,

    @ColumnInfo(name = "is_recurring_default", defaultValue = "0")
    val isRecurringDefault: Boolean = false,
)
