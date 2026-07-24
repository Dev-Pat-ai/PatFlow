package com.patflow.app.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * Cloud Sync State (Architecture §v2.0 Foundation).
 */
@Serializable
enum class SyncState {
    IDLE,
    SYNCING,
    SUCCESS,
    ERROR,
    UNAUTHORIZED
}

/**
 * Result of a sync operation.
 */
sealed interface SyncResult {
    data object Success : SyncResult
    data class Error(val message: String) : SyncResult
    data class Conflict(val local: Any, val remote: Any) : SyncResult
}

/**
 * Conflict resolution strategy.
 */
enum class ConflictStrategy {
    USE_LOCAL,
    USE_REMOTE,
    USE_LATEST,
    MANUAL
}

/**
 * Domain model for a sync log entry.
 */
data class SyncLog(
    val id: Long = 0,
    val lastSyncAt: LocalDateTime,
    val status: SyncState,
    val message: String? = null
)
