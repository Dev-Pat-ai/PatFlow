package com.patflow.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.BackupModel
import com.patflow.app.domain.usecase.datamanagement.CreateBackupUseCase
import com.patflow.app.domain.usecase.datamanagement.ExportCsvUseCase
import com.patflow.app.domain.usecase.datamanagement.RestoreBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * ViewModel for data persistence operations (Backup, Restore, Export) (Architecture §6).
 * Interfaces with [ActivityResultLauncher] patterns in the UI via [eventFlow].
 */
@HiltViewModel
class DataManagementViewModel @Inject constructor(
    private val createBackupUseCase: CreateBackupUseCase,
    private val restoreBackupUseCase: RestoreBackupUseCase,
    private val exportCsvUseCase: ExportCsvUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DataManagementUiState>(DataManagementUiState.Idle)
    val uiState: StateFlow<DataManagementUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun createBackup() {
        viewModelScope.launch {
            _uiState.value = DataManagementUiState.Loading("Generating backup...")
            try {
                val backup = createBackupUseCase()
                val json = Json { prettyPrint = true }.encodeToString(BackupModel.serializer(), backup)
                _eventFlow.emit(UiEvent.SaveFile(json, "patflow_backup.json", "application/json"))
                _uiState.value = DataManagementUiState.Idle
            } catch (e: Exception) {
                _uiState.value = DataManagementUiState.Error(e.message ?: "Failed to create backup")
            }
        }
    }

    fun restoreBackup(jsonContent: String) {
        viewModelScope.launch {
            _uiState.value = DataManagementUiState.Loading("Validating backup...")
            try {
                val backup = Json.decodeFromString(BackupModel.serializer(), jsonContent)
                restoreBackupUseCase(backup)
                _eventFlow.emit(UiEvent.ShowSuccess("Data restored successfully. The app will now refresh."))
                _uiState.value = DataManagementUiState.Idle
            } catch (e: Exception) {
                _uiState.value = DataManagementUiState.Error("Restore failed: ${e.message}")
            }
        }
    }

    fun exportCsv() {
        viewModelScope.launch {
            _uiState.value = DataManagementUiState.Loading("Preparing CSV export...")
            try {
                val csvData = exportCsvUseCase()
                // For simplicity, we'll emit one event to save a ZIP or multiple files
                // Here we'll just save the bills CSV as an example, 
                // or ideally we'd package them.
                csvData.forEach { (filename, content) ->
                    _eventFlow.emit(UiEvent.SaveFile(content, filename, "text/csv"))
                }
                _uiState.value = DataManagementUiState.Idle
            } catch (e: Exception) {
                _uiState.value = DataManagementUiState.Error(e.message ?: "Failed to export CSV")
            }
        }
    }

    fun clearError() {
        _uiState.value = DataManagementUiState.Idle
    }

    sealed interface DataManagementUiState {
        data object Idle : DataManagementUiState
        data class Loading(val message: String) : DataManagementUiState
        data class Error(val message: String) : DataManagementUiState
    }

    sealed interface UiEvent {
        data class SaveFile(val content: String, val filename: String, val mimeType: String) : UiEvent
        data class ShowSuccess(val message: String) : UiEvent
    }
}
