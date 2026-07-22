package com.patflow.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

/** `recent_search` — last 10 locally stored searches (Architecture §8.7 / FR-17.2). */
@Entity(
    tableName = "recent_search",
    indices = [Index(value = ["searched_at"])],
)
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "query_text")
    val queryText: String,

    @ColumnInfo(name = "searched_at")
    val searchedAt: LocalDateTime,
)
