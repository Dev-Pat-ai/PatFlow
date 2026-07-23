package com.patflow.app.domain.usecase.datamanagement

import com.patflow.app.domain.model.BackupModel
import com.patflow.app.domain.repository.DataManagementRepository
import javax.inject.Inject

/**
 * Orchestrates the creation of a full application backup.
 */
class CreateBackupUseCase @Inject constructor(
    private val repository: DataManagementRepository
) {
    suspend operator fun invoke(): BackupModel = repository.createBackup()
}
