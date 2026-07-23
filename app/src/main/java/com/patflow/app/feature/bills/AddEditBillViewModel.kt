package com.patflow.app.feature.bills

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.model.Category
import com.patflow.app.domain.model.Recurrence
import com.patflow.app.domain.model.RecurrenceType
import com.patflow.app.domain.repository.CategoryRepository
import com.patflow.app.domain.usecase.bill.AddBillUseCase
import com.patflow.app.domain.usecase.bill.GetBillDetailUseCase
import com.patflow.app.domain.usecase.bill.UpdateBillUseCase
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * ViewModel for the Add/Edit Bill screen.
 * Manages form state, validation, and persistence logic.
 */
@HiltViewModel
class AddEditBillViewModel @Inject constructor(
    private val addBillUseCase: AddBillUseCase,
    private val updateBillUseCase: UpdateBillUseCase,
    private val getBillDetailUseCase: GetBillDetailUseCase,
    private val categoryRepository: CategoryRepository,
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val billId: Long? = savedStateHandle.get<String>("billId")?.toLongOrNull()

    private val _uiState = MutableStateFlow(AddEditBillUiState())
    val uiState: StateFlow<AddEditBillUiState> = _uiState

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        loadCategories()
        loadPreferredSettings()
        billId?.let { id ->
            loadBill(id)
        }
    }

    private fun loadPreferredSettings() {
        viewModelScope.launch {
            val settings = getUserSettingsUseCase().first()
            _uiState.update { it.copy(currencyCode = settings.profile.preferredCurrency) }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories().collect {
                _categories.value = it
                if (_uiState.value.category == null && it.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(category = it.first())
                }
            }
        }
    }

    private fun loadBill(id: Long) {
        viewModelScope.launch {
            getBillDetailUseCase(id).first()?.let { detail ->
                val bill = detail.bill
                _uiState.value = _uiState.value.copy(
                    name = bill.name,
                    amount = bill.defaultAmount.toString(),
                    currencyCode = bill.currencyCode,
                    category = bill.category,
                    startDate = bill.recurrence.startDate,
                    recurrenceType = bill.recurrence.type,
                    notes = bill.notes ?: "",
                    isEditMode = true
                )
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name, nameError = null)
    }

    fun onAmountChange(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount, amountError = null)
    }

    fun onCategoryChange(category: Category) {
        _uiState.value = _uiState.value.copy(category = category, categoryError = null)
    }

    fun onDateChange(date: LocalDate?) {
        date?.let {
            _uiState.value = _uiState.value.copy(startDate = it)
        }
    }

    fun onRecurrenceTypeChange(type: RecurrenceType) {
        _uiState.value = _uiState.value.copy(recurrenceType = type)
    }

    fun onNotesChange(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes)
    }

    fun saveBill() {
        val state = _uiState.value
        val nameError = state.name.isBlank()
        val amountError = state.amount.toDoubleOrNull() == null
        val categoryError = state.category == null

        if (nameError || amountError || categoryError) {
            _uiState.value = state.copy(
                nameError = if (nameError) "Name cannot be empty" else null,
                amountError = if (amountError) "Enter a valid amount" else null,
                categoryError = if (categoryError) "Select a category" else null
            )
            return
        }

        viewModelScope.launch {
            val bill = Bill(
                id = billId ?: 0,
                name = state.name,
                category = state.category,
                defaultAmount = state.amount.toDouble(),
                currencyCode = state.currencyCode,
                recurrence = Recurrence(
                    type = state.recurrenceType,
                    startDate = state.startDate
                ),
                notes = state.notes.ifBlank { null }
            )

            if (state.isEditMode) {
                updateBillUseCase(bill)
            } else {
                addBillUseCase(bill)
            }
            _eventFlow.emit(UiEvent.SaveSuccess)
        }
    }

    sealed interface UiEvent {
        data object SaveSuccess : UiEvent
    }
}

data class AddEditBillUiState(
    val name: String = "",
    val nameError: String? = null,
    val amount: String = "",
    val amountError: String? = null,
    val currencyCode: String = "PHP",
    val category: Category? = null,
    val categoryError: String? = null,
    val startDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val recurrenceType: RecurrenceType = RecurrenceType.MONTHLY,
    val notes: String = "",
    val isEditMode: Boolean = false
)
