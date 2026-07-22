package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** `income_category` — seeded with Salary, Allowance, Freelance, Business, Others (Architecture §8.4 / FR-12.1). */
@Entity(tableName = "income_category")
data class IncomeCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    @ColumnInfo(name = "icon_key")
    val iconKey: String,

    @ColumnInfo(name = "color_hex")
    val colorHex: String,

    @ColumnInfo(name = "is_custom", defaultValue = "0")
    val isCustom: Boolean = false,
)
