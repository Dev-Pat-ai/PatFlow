package com.patflow.app.data.repository

import com.patflow.app.domain.model.SyncLog
import com.patflow.app.domain.model.SyncResult
import com.patflow.app.domain.model.SyncState
import com.patflow.app.domain.repository.SyncRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SyncRepositoryImpl @Inject constructor() : SyncRepository {
    override val syncState: Flow<SyncState> = MutableStateFlow(SyncState.IDLE)

    override suspend fun syncAll(): SyncResult = SyncResult.Success
    override suspend fun pushChanges(): SyncResult = SyncResult.Success
    override suspend fun pullChanges(): SyncResult = SyncResult.Success

    override fun getSyncLogs(): Flow<List<SyncLog>> = flowOf(emptyList())
}
