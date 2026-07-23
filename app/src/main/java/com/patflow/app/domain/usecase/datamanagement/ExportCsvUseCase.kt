package com.patflow.app.domain.usecase.datamanagement

import com.patflow.app.domain.repository.DataManagementRepository
import javax.inject.Inject

/**
 * Generates CSV data for user export.
 */
class ExportCsvUseCase @Inject constructor(
    private val repository: DataManagementRepository
) {
    suspend operator fun invoke(): Map<String, String> = repository.generateCsvExport()
}
