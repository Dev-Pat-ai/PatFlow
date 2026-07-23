package com.patflow.app.feature.income

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.IncomeCategory
import com.patflow.app.domain.model.IncomeEntry
import com.patflow.app.domain.model.IncomeSource
import com.patflow.app.domain.model.Recurrence
import com.patflow.app.domain.model.RecurrenceType
import com.patflow.app.domain.repository.IncomeRepository
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
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

/**
 * ViewModel for adding or editing an income entry.
 */
@HiltViewModel
class AddEditIncomeViewModel @Inject constructor(
    private val repository: IncomeRepository,
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val entryId: Long? = savedStateHandle.get<String>("entryId")?.toLongOrNull()

    private val _uiState = MutableStateFlow(AddEditIncomeUiState())
    val uiState: StateFlow<AddEditIncomeUiState> = _uiState

    private val _categories = MutableStateFlow<List<IncomeCategory>>(emptyList())
    val categories: StateFlow<List<IncomeCategory>> = _categories

    private val _sources = MutableStateFlow<List<IncomeSource>>(emptyList())
    val sources: StateFlow<List<IncomeSource>> = _sources

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        loadCategories()
        loadSources()
        loadPreferredSettings()
        entryId?.let { id ->
            loadEntry(id)
        }
    }

    private fun loadSources() {
        viewModelScope.launch {
            repository.getSources().collect {
                _sources.value = it.filter { !it.isDeleted && !it.isArchived }
            }
        }
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

    private fun loadPreferredSettings() {
        viewModelScope.launch {
            val settings = getUserSettingsUseCase().first()
            _uiState.value = _uiState.value.copy(currencyCode = settings.profile.preferredCurrency)
        }
    }

    private fun loadEntry(id: Long) {
        viewModelScope.launch {
            repository.getEntryById(id)?.let { detail ->
                val entry = detail.entry
                _uiState.value = _uiState.value.copy(
                    amount = entry.amount.toString(),
                    currencyCode = entry.currencyCode,
                    category = entry.category,
                    date = entry.entryDate,
                    note = entry.note ?: "",
                    isEditMode = true
                )
            }
        }
    }

    fun onAmountChange(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount, amountError = null)
    }

    fun onCategoryChange(category: IncomeCategory) {
        _uiState.value = _uiState.value.copy(category = category, categoryError = null)
    }

    fun onDateChange(date: LocalDate?) {
        date?.let {
            _uiState.value = _uiState.value.copy(date = it)
        }
    }

    fun onNoteChange(note: String) {
        _uiState.value = _uiState.value.copy(note = note)
    }

    fun onSourceChange(source: IncomeSource?) {
        _uiState.value = _uiState.value.copy(
            incomeSourceId = source?.id,
            category = source?.category ?: _uiState.value.category,
            amount = source?.defaultAmount?.toString() ?: _uiState.value.amount
        )
    }

    fun saveEntry() {
        val state = _uiState.value
        val amountValue = state.amount.toDoubleOrNull()
        
        if (amountValue == null || state.category == null) {
            _uiState.value = state.copy(
                amountError = if (amountValue == null) "Invalid amount" else null,
                categoryError = if (state.category == null) "Select a category" else null
            )
            return
        }

        viewModelScope.launch {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val entry = IncomeEntry(
                id = entryId ?: 0,
                incomeSourceId = state.incomeSourceId,
                category = state.category,
                amount = amountValue,
                currencyCode = state.currencyCode,
                entryDate = state.date,
                note = state.note.ifBlank { null },
                createdAt = now
            )

            if (state.isEditMode) {
                repository.updateEntry(entry)
            } else {
                repository.insertEntry(entry)
            }
            _eventFlow.emit(UiEvent.SaveSuccess)
        }
    }

    sealed interface UiEvent {
        data object SaveSuccess : UiEvent
    }
}

data class AddEditIncomeUiState(
    val amount: String = "",
    val amountError: String? = null,
    val currencyCode: String = "PHP",
    val category: IncomeCategory? = null,
    val categoryError: String? = null,
    val incomeSourceId: Long? = null,
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val note: String = "",
    val isEditMode: Boolean = false
)
