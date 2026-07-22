package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

/**
 * `bill_search_fts` — Room FTS4 virtual table indexing bill name/notes/merchant
 * and the denormalized category name (Architecture §8.7 / FR-17.1).
 *
 * Kept in sync by the repository on every bill insert/update — Room FTS
 * doesn't auto-trigger off a separate table, so `SearchDao` (data/local/dao/)
 * writes to this table explicitly alongside `bill` writes.
 *
 * `billId` is excluded from full-text indexing (notIndexed) since it's a
 * join key, not searchable text; queries MATCH against name/notes/merchant/
 * categoryName and then read `billId` off the matched row to join back to
 * `bill`/`bill_cycle` for display.
 */
@Fts4(notIndexed = ["billId"])
@Entity(tableName = "bill_search_fts")
data class BillSearchFtsEntity(
    @ColumnInfo(name = "billId")
    val billId: Long,

    val name: String,

    val notes: String,

    val merchant: String,

    /** Denormalized at write time from bill_category.name, since FTS4 can't join. */
    @ColumnInfo(name = "categoryName")
    val categoryName: String,
)
