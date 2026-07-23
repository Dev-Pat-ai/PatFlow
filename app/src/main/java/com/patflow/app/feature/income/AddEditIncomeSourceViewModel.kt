package com.patflow.app.feature.income

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.IncomeCategory
import com.patflow.app.domain.model.IncomeSource
import com.patflow.app.domain.model.Recurrence
import com.patflow.app.domain.model.RecurrenceType
import com.patflow.app.domain.repository.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddEditIncomeSourceViewModel @Inject constructor(
    private val repository: IncomeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sourceId: Long? = savedStateHandle.get<String>("sourceId")?.toLongOrNull()

    private val _uiState = MutableStateFlow(AddEditIncomeSourceUiState())
    val uiState: StateFlow<AddEditIncomeSourceUiState> = _uiState

    private val _categories = MutableStateFlow<List<IncomeCategory>>(emptyList())
    val categories: StateFlow<List<IncomeCategory>> = _categories

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        loadCategories()
        sourceId?.let { loadSource(it) }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getCategories().collect {
                _categories.value = it
                if (_uiState.value.category == null && it.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(category = it.first())
                }
            }
        }
    }

    private fun loadSource(id: Long) {
        viewModelScope.launch {
            repository.getSourceById(id)?.let { source ->
                _uiState.value = _uiState.value.copy(
                    name = source.name,
                    amount = source.defaultAmount.toString(),
                    category = source.category,
                    startDate = source.recurrence.startDate,
                    recurrenceType = source.recurrence.type,
                    recurrenceInterval = source.recurrence.interval,
                    isActive = source.isActive,
                    isEditMode = true
                )
            }
        }
    }

    fun onNameChange(name: String) { _uiState.value = _uiState.value.copy(name = name) }
    fun onAmountChange(amount: String) { _uiState.value = _uiState.value.copy(amount = amount) }
    fun onCategoryChange(category: IncomeCategory) { _uiState.value = _uiState.value.copy(category = category) }
    fun onDateChange(date: LocalDate) { _uiState.value = _uiState.value.copy(startDate = date) }
    fun onRecurrenceTypeChange(type: RecurrenceType) { _uiState.value = _uiState.value.copy(recurrenceType = type) }
    fun onRecurrenceIntervalChange(interval: Int) { _uiState.value = _uiState.value.copy(recurrenceInterval = interval) }

    fun toggleActive() {
        _uiState.value = _uiState.value.copy(isActive = !_uiState.value.isActive)
    }

    fun saveSource() {
        val state = _uiState.value
        val amountValue = state.amount.toDoubleOrNull() ?: return
        if (state.category == null) return

        viewModelScope.launch {
            val source = IncomeSource(
                id = sourceId ?: 0,
                category = state.category,
                name = state.name,
                defaultAmount = amountValue,
                recurrence = Recurrence(
                    type = state.recurrenceType,
                    interval = state.recurrenceInterval,
                    startDate = state.startDate
                ),
                isActive = state.isActive
            )
            if (sourceId == null) repository.insertSource(source) else repository.updateSource(source)
            _eventFlow.emit(UiEvent.SaveSuccess)
        }
    }

    sealed interface UiEvent {
        data object SaveSuccess : UiEvent
    }
}

data class AddEditIncomeSourceUiState(
    val name: String = "",
    val amount: String = "",
    val category: IncomeCategory? = null,
    val startDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val recurrenceType: RecurrenceType = RecurrenceType.MONTHLY,
    val recurrenceInterval: Int = 1,
    val isActive: Boolean = true,
    val isEditMode: Boolean = false
)
