package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * `bill_category` (Architecture §8.3). 11 predefined categories (is_custom = false)
 * seeded on first run, plus user-defined Custom categories.
 */
@Entity(tableName = "bill_category")
data class BillCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    @ColumnInfo(name = "icon_key")
    val iconKey: String,

    @ColumnInfo(name = "color_hex")
    val colorHex: String,

    @ColumnInfo(name = "is_custom")
    val isCustom: Boolean = false,

    @ColumnInfo(name = "is_deleted", defaultValue = "0")
    val isDeleted: Boolean = false,

    // --- v2 sync scaffold (Architecture §8.1) ---
    @ColumnInfo(name = "remote_id")
    val remoteId: String? = null,

    @ColumnInfo(name = "sync_status", defaultValue = "LOCAL_ONLY")
    val syncStatus: String = "LOCAL_ONLY",
)
