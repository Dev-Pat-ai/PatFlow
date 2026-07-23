package com.patflow.app.domain.repository

import com.patflow.app.domain.model.BackupModel
import kotlinx.coroutines.flow.Flow

/**
 * Interface for backup, restore, and export operations (Architecture §1.10 / Phase 7B).
 */
interface DataManagementRepository {
    
    /** Fetches a complete backup model of current data. */
    suspend fun createBackup(): BackupModel
    
    /** 
     * Replaces existing data with the provided backup. 
     * Executes inside a transaction.
     */
    suspend fun restoreBackup(backup: BackupModel)
    
    /**
     * Generates CSV content for specific tables.
     * @return Map of filename to CSV string.
     */
    suspend fun generateCsvExport(): Map<String, String>
}
