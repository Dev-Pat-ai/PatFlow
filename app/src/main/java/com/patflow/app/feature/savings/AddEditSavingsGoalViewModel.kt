package com.patflow.app.feature.savings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.SavingsGoal
import com.patflow.app.domain.repository.SavingsGoalRepository
import com.patflow.app.domain.usecase.savings.AddSavingsGoalUseCase
import com.patflow.app.domain.usecase.savings.UpdateSavingsGoalUseCase
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddEditSavingsGoalViewModel @Inject constructor(
    private val repository: SavingsGoalRepository,
    private val addGoalUseCase: AddSavingsGoalUseCase,
    private val updateGoalUseCase: UpdateSavingsGoalUseCase,
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val goalId: Long? = savedStateHandle.get<String>("goalId")?.toLongOrNull()

    private val _uiState = MutableStateFlow(AddEditSavingsGoalUiState())
    val uiState: StateFlow<AddEditSavingsGoalUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        loadPreferredSettings()
        goalId?.let { loadGoal(it) }
    }

    private fun loadPreferredSettings() {
        viewModelScope.launch {
            val settings = getUserSettingsUseCase().first()
            _uiState.update { it.copy(currencyCode = settings.profile.preferredCurrency) }
        }
    }

    private fun loadGoal(id: Long) {
        viewModelScope.launch {
            repository.getGoalById(id)?.let { goal ->
                _uiState.update { it.copy(
                    name = goal.name,
                    targetAmount = goal.targetAmount.toString(),
                    targetDate = goal.targetDate,
                    iconKey = goal.iconKey,
                    colorHex = goal.colorHex,
                    notes = goal.notes ?: "",
                    isEditMode = true
                ) }
            }
        }
    }

    fun onNameChange(name: String) { _uiState.update { it.copy(name = name) } }
    fun onAmountChange(amount: String) { _uiState.update { it.copy(targetAmount = amount) } }
    fun onDateChange(date: LocalDate?) { _uiState.update { it.copy(targetDate = date) } }
    fun onNotesChange(notes: String) { _uiState.update { it.copy(notes = notes) } }

    fun saveGoal() {
        val state = _uiState.value
        val amountValue = state.targetAmount.toDoubleOrNull() ?: return

        viewModelScope.launch {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val goal = SavingsGoal(
                id = goalId ?: 0,
                name = state.name,
                targetAmount = amountValue,
                currencyCode = state.currencyCode,
                targetDate = state.targetDate,
                iconKey = state.iconKey,
                colorHex = state.colorHex,
                notes = state.notes.ifBlank { null },
                createdAt = now
            )
            
            if (goalId == null) addGoalUseCase(goal) else updateGoalUseCase(goal)
            _eventFlow.emit(UiEvent.SaveSuccess)
        }
    }

    sealed interface UiEvent {
        data object SaveSuccess : UiEvent
    }
}

data class AddEditSavingsGoalUiState(
    val name: String = "",
    val targetAmount: String = "",
    val currencyCode: String = "PHP",
    val targetDate: LocalDate? = null,
    val iconKey: String = "savings",
    val colorHex: String = "#2E8B57",
    val notes: String = "",
    val isEditMode: Boolean = false
)
