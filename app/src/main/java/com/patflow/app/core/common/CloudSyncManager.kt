package com.patflow.app.core.common

import com.patflow.app.domain.model.SyncResult
import com.patflow.app.domain.repository.AuthRepository
import com.patflow.app.domain.repository.SyncRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Orchestrator for background and foreground synchronization (v2.0 Foundation).
 */
@Singleton
class CloudSyncManager @Inject constructor(
    private val authRepository: AuthRepository,
    private val syncRepository: SyncRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Triggers a manual synchronization if the user is authenticated.
     */
    fun triggerSync() {
        scope.launch {
            if (authRepository.isAuthenticated.first()) {
                syncRepository.syncAll()
            }
        }
    }
}
