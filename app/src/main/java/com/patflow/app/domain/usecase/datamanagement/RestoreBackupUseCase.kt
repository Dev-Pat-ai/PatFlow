package com.patflow.app.domain.usecase.datamanagement

import com.patflow.app.domain.model.BackupModel
import com.patflow.app.domain.repository.DataManagementRepository
import javax.inject.Inject

/**
 * Validates and restores a provided backup to the system.
 */
class RestoreBackupUseCase @Inject constructor(
    private val repository: DataManagementRepository
) {
    suspend operator fun invoke(backup: BackupModel) {
        // Validation logic moves here in a production app
        if (backup.schemaVersion > 1) {
            throw IllegalArgumentException("Unsupported backup schema version: ${backup.schemaVersion}")
        }
        repository.restoreBackup(backup)
    }
}
