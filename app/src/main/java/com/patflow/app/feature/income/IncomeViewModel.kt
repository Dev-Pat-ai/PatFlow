package com.patflow.app.feature.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.IncomeCategory
import com.patflow.app.domain.model.IncomeWithDetails
import com.patflow.app.domain.repository.IncomeRepository
import com.patflow.app.domain.usecase.income.AddIncomeEntryUseCase
import com.patflow.app.domain.usecase.income.GetIncomeEntriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the Income List screen.
 * Manages searching, filtering, and aggregation of income data.
 */
@HiltViewModel
class IncomeViewModel @Inject constructor(
    getIncomeEntriesUseCase: GetIncomeEntriesUseCase,
    private val repository: IncomeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    val selectedCategoryId: StateFlow<Long?> = _selectedCategoryId

    val categories: StateFlow<List<IncomeCategory>> = repository.getCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val uiState: StateFlow<IncomeUiState> = combine(
        getIncomeEntriesUseCase(),
        _searchQuery,
        _selectedCategoryId
    ) { entries, query, categoryId ->
        val filtered = entries.filter { item ->
            val matchesQuery = item.entry.note?.contains(query, ignoreCase = true) ?: true ||
                    item.sourceName?.contains(query, ignoreCase = true) ?: true
            val matchesCategory = categoryId == null || item.entry.category.id == categoryId
            matchesQuery && matchesCategory
        }.sortedByDescending { it.entry.entryDate }

        IncomeUiState.Success(filtered)
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

    suspend fun deleteEntry(id: Long) {
        repository.deleteEntry(id)
    }
}

sealed interface IncomeUiState {
    data object Loading : IncomeUiState
    data class Success(val entries: List<IncomeWithDetails>) : IncomeUiState
    data class Error(val message: String) : IncomeUiState
}
