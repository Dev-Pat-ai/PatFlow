package com.patflow.app.feature.budget

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.Budget
import com.patflow.app.domain.model.BudgetType
import com.patflow.app.domain.repository.BudgetRepository
import com.patflow.app.domain.repository.CategoryRepository
import com.patflow.app.domain.usecase.budget.AddBudgetUseCase
import com.patflow.app.domain.usecase.budget.UpdateBudgetUseCase
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddEditBudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val addBudgetUseCase: AddBudgetUseCase,
    private val updateBudgetUseCase: UpdateBudgetUseCase,
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val budgetId: Long? = savedStateHandle.get<String>("budgetId")?.toLongOrNull()

    private val _uiState = MutableStateFlow(AddEditBudgetUiState())
    val uiState: StateFlow<AddEditBudgetUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        loadPreferredSettings()
        budgetId?.let { loadBudget(it) }
    }

    private fun loadPreferredSettings() {
        viewModelScope.launch {
            val settings = getUserSettingsUseCase().first()
            _uiState.update { it.copy(currencyCode = settings.profile.preferredCurrency) }
        }
    }

    private fun loadBudget(id: Long) {
        viewModelScope.launch {
            budgetRepository.getBudgetById(id)?.let { budget ->
                _uiState.update { it.copy(
                    name = budget.name,
                    amount = budget.totalAmount.toString(),
                    type = budget.type,
                    startDate = budget.startDate,
                    endDate = budget.endDate,
                    isEditMode = true
                ) }
            }
        }
    }

    fun onNameChange(name: String) { _uiState.update { it.copy(name = name) } }
    fun onAmountChange(amount: String) { _uiState.update { it.copy(amount = amount) } }
    fun onTypeChange(type: BudgetType) { 
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val endDate = when (type) {
            BudgetType.MONTHLY -> LocalDate(now.year, now.month, 1).plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
            BudgetType.WEEKLY -> now.plus(DatePeriod(days = 6))
            BudgetType.YEARLY -> LocalDate(now.year, 1, 1).plus(DatePeriod(years = 1)).minus(DatePeriod(days = 1))
            else -> now
        }
        _uiState.update { it.copy(type = type, endDate = endDate) } 
    }
    fun onStartDateChange(date: LocalDate) { _uiState.update { it.copy(startDate = date) } }
    fun onEndDateChange(date: LocalDate) { _uiState.update { it.copy(endDate = date) } }

    fun saveBudget() {
        val state = _uiState.value
        val amountValue = state.amount.toDoubleOrNull() ?: return
        
        viewModelScope.launch {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val budget = Budget(
                id = budgetId ?: 0,
                name = state.name.ifBlank { "${state.type.name} Budget" },
                type = state.type,
                totalAmount = amountValue,
                currencyCode = state.currencyCode,
                startDate = state.startDate,
                endDate = state.endDate,
                createdAt = now,
                updatedAt = now
            )
            
            if (budgetId == null) addBudgetUseCase(budget) else updateBudgetUseCase(budget)
            _eventFlow.emit(UiEvent.SaveSuccess)
        }
    }

    sealed interface UiEvent {
        data object SaveSuccess : UiEvent
    }
}

data class AddEditBudgetUiState(
    val name: String = "",
    val amount: String = "",
    val currencyCode: String = "PHP",
    val type: BudgetType = BudgetType.MONTHLY,
    val startDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val endDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.plus(DatePeriod(months = 1)),
    val isEditMode: Boolean = false
)
