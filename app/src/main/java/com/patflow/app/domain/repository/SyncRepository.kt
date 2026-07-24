package com.patflow.app.domain.repository

import com.patflow.app.domain.model.SyncResult
import com.patflow.app.domain.model.SyncState
import kotlinx.coroutines.flow.Flow

/**
 * Contract for data synchronization between local and remote sources (v2.0 Foundation).
 */
interface SyncRepository {
    val syncState: Flow<SyncState>
    
    suspend fun syncAll(): SyncResult
    suspend fun pushChanges(): SyncResult
    suspend fun pullChanges(): SyncResult
    
    fun getSyncLogs(): Flow<List<com.patflow.app.domain.model.SyncLog>>
}
