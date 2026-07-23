package com.patflow.app.feature.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.IncomeCategory
import com.patflow.app.domain.model.IncomeSortOrder
import com.patflow.app.domain.model.IncomeWithDetails
import com.patflow.app.domain.repository.IncomeRepository
import com.patflow.app.domain.usecase.income.DeleteIncomeEntryUseCase
import com.patflow.app.domain.usecase.income.DuplicateIncomeEntryUseCase
import com.patflow.app.domain.usecase.income.GetIncomeEntriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

/**
 * ViewModel for the Income List screen.
 * Manages searching, filtering, and aggregation of income data.
 */
@HiltViewModel
class IncomeViewModel @Inject constructor(
    getIncomeEntriesUseCase: GetIncomeEntriesUseCase,
    private val deleteIncomeEntryUseCase: DeleteIncomeEntryUseCase,
    private val duplicateIncomeEntryUseCase: DuplicateIncomeEntryUseCase,
    private val repository: IncomeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    val selectedCategoryId: StateFlow<Long?> = _selectedCategoryId.asStateFlow()

    private val _dateRange = MutableStateFlow<Pair<LocalDate?, LocalDate?>>(null to null)
    val dateRange: StateFlow<Pair<LocalDate?, LocalDate?>> = _dateRange.asStateFlow()

    private val _amountRange = MutableStateFlow<Pair<Double?, Double?>>(null to null)
    val amountRange: StateFlow<Pair<Double?, Double?>> = _amountRange.asStateFlow()

    private val _onlyRecurring = MutableStateFlow(false)
    val onlyRecurring: StateFlow<Boolean> = _onlyRecurring.asStateFlow()

    private val _selectedIds = MutableStateFlow<Set<Long>>(emptySet())
    val selectedIds: StateFlow<Set<Long>> = _selectedIds.asStateFlow()

    private val _sortBy = MutableStateFlow(IncomeSortOrder.NEWEST)
    val sortBy: StateFlow<IncomeSortOrder> = _sortBy.asStateFlow()

    val categories: StateFlow<List<IncomeCategory>> = repository.getCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val uiState: StateFlow<IncomeUiState> = combine(
        getIncomeEntriesUseCase(),
        _searchQuery,
        _selectedCategoryId,
        _dateRange,
        _amountRange,
        _onlyRecurring,
        _selectedIds,
        _sortBy
    ) { args: Array<Any?> ->
        @Suppress("UNCHECKED_CAST")
        val entries = args[0] as List<IncomeWithDetails>
        val query = args[1] as String
        val categoryId = args[2] as Long?
        @Suppress("UNCHECKED_CAST")
        val dates = args[3] as Pair<LocalDate?, LocalDate?>
        @Suppress("UNCHECKED_CAST")
        val amounts = args[4] as Pair<Double?, Double?>
        val recurring = args[5] as Boolean
        @Suppress("UNCHECKED_CAST")
        val selectedIds = args[6] as Set<Long>
        val sort = args[7] as IncomeSortOrder

        val filtered = entries.filter { item ->
            val matchesQuery = (item.entry.note?.contains(query, ignoreCase = true) ?: true) ||
                    (item.sourceName?.contains(query, ignoreCase = true) ?: true)
            val matchesCategory = categoryId == null || item.entry.category.id == categoryId
            
            val (startDate, endDate) = dates
            val matchesDate = (startDate == null || item.entry.entryDate >= startDate) &&
                              (endDate == null || item.entry.entryDate <= endDate)
            
            val (minAmount, maxAmount) = amounts
            val matchesAmount = (minAmount == null || item.entry.amount >= minAmount) &&
                                (maxAmount == null || item.entry.amount <= maxAmount)
            
            val matchesRecurring = !recurring || item.entry.incomeSourceId != null
            
            matchesQuery && matchesCategory && matchesDate && matchesAmount && matchesRecurring
        }.sortedWith { a, b ->
            when (sort) {
                IncomeSortOrder.NEWEST -> b.entry.entryDate.compareTo(a.entry.entryDate)
                IncomeSortOrder.OLDEST -> a.entry.entryDate.compareTo(b.entry.entryDate)
                IncomeSortOrder.HIGHEST_AMOUNT -> b.entry.amount.compareTo(a.entry.amount)
                IncomeSortOrder.LOWEST_AMOUNT -> a.entry.amount.compareTo(b.entry.amount)
                IncomeSortOrder.ALPHABETICAL -> (a.sourceName ?: "").compareTo(b.sourceName ?: "")
            }
        }

        IncomeUiState.Success(filtered, selectedIds)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = IncomeUiState.Loading
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategoryFilterChange(categoryId: Long?) {
        _selectedCategoryId.value = categoryId
    }

    fun onDateRangeChange(start: LocalDate?, end: LocalDate?) {
        _dateRange.value = start to end
    }

    fun onAmountRangeChange(min: Double?, max: Double?) {
        _amountRange.value = min to max
    }

    fun onRecurringToggle(onlyRecurring: Boolean) {
        _onlyRecurring.value = onlyRecurring
    }

    fun onSortOrderChange(order: IncomeSortOrder) {
        _sortBy.value = order
    }

    fun toggleSelection(id: Long) {
        _selectedIds.value = if (_selectedIds.value.contains(id)) {
            _selectedIds.value - id
        } else {
            _selectedIds.value + id
        }
    }

    fun clearSelection() {
        _selectedIds.value = emptySet()
    }

    fun deleteEntry(id: Long) {
        viewModelScope.launch {
            deleteIncomeEntryUseCase(id)
        }
    }

    fun duplicateEntry(id: Long) {
        viewModelScope.launch {
            duplicateIncomeEntryUseCase(id)
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            _selectedIds.value.forEach { deleteIncomeEntryUseCase(it) }
            clearSelection()
        }
    }
}

sealed interface IncomeUiState {
    data object Loading : IncomeUiState
    data class Success(
        val entries: List<IncomeWithDetails>,
        val selectedIds: Set<Long> = emptySet()
    ) : IncomeUiState
    data class Error(val message: String) : IncomeUiState
}
