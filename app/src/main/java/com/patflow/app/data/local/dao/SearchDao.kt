package com.patflow.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patflow.app.data.local.entity.BillSearchFtsEntity
import com.patflow.app.data.local.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

/**
 * Minimal CRUD for `recent_search` and `bill_search_fts`. The hybrid
 * FTS-plus-structured-query merge logic (SearchBillsUseCase, Architecture
 * §8.7) lands with the Search feature — this DAO only exposes the raw
 * FTS match and recent-search persistence.
 */
@Dao
interface SearchDao {

    // ---- bill_search_fts ----
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFtsEntry(entry: BillSearchFtsEntity)

    @Query("DELETE FROM bill_search_fts WHERE billId = :billId")
    suspend fun deleteFtsEntry(billId: Long)

    @Query("SELECT * FROM bill_search_fts WHERE bill_search_fts MATCH :query")
    suspend fun matchFts(query: String): List<BillSearchFtsEntity>

    // ---- recent_search ----
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(search: RecentSearchEntity): Long

    @Delete
    suspend fun deleteRecentSearch(search: RecentSearchEntity)

    @Query("DELETE FROM recent_search")
    suspend fun clearRecentSearches()

    @Query("SELECT * FROM recent_search ORDER BY searched_at DESC LIMIT :limit")
    fun getRecentSearches(limit: Int = 10): Flow<List<RecentSearchEntity>>
}
