package com.patflow.app.domain.repository

import com.patflow.app.domain.model.IncomeCategory
import com.patflow.app.domain.model.IncomeEntry
import com.patflow.app.domain.model.IncomeSource
import com.patflow.app.domain.model.IncomeWithDetails
import kotlinx.coroutines.flow.Flow

/**
 * Interface for income-related data operations (Architecture §8.4).
 */
interface IncomeRepository {
    
    // ---- Categories ----
    fun getCategories(): Flow<List<IncomeCategory>>
    suspend fun insertCategory(category: IncomeCategory): Long
    
    // ---- Sources ----
    fun getSources(): Flow<List<IncomeSource>>
    suspend fun getSourceById(id: Long): IncomeSource?
    suspend fun insertSource(source: IncomeSource): Long
    suspend fun updateSource(source: IncomeSource)
    suspend fun deleteSource(id: Long)
    suspend fun archiveSource(id: Long, archived: Boolean)
    
    // ---- Entries ----
    fun getEntries(): Flow<List<IncomeWithDetails>>
    fun getEntriesByDateRange(start: String, end: String): Flow<List<IncomeWithDetails>>
    suspend fun getEntryById(id: Long): IncomeWithDetails?
    suspend fun insertEntry(entry: IncomeEntry): Long
    suspend fun updateEntry(entry: IncomeEntry)
    suspend fun deleteEntry(id: Long)
}
