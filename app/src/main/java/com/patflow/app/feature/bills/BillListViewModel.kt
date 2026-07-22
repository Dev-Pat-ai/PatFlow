package com.patflow.app.feature.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.usecase.bill.GetBillsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BillListViewModel @Inject constructor(
    getBillsUseCase: GetBillsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedStatus = MutableStateFlow<BillStatus?>(null)
    val selectedStatus: StateFlow<BillStatus?> = _selectedStatus

    private val _sortByDateDesc = MutableStateFlow(false)
    val sortByDateDesc: StateFlow<Boolean> = _sortByDateDesc

    val uiState: StateFlow<BillListUiState> = combine(
        getBillsUseCase(),
        _searchQuery,
        _selectedStatus,
        _sortByDateDesc
    ) { bills, query, status, sortDesc ->
        val filtered = bills.filter { item ->
            val matchesQuery = item.bill.name.contains(query, ignoreCase = true) ||
                    (item.bill.merchant?.contains(query, ignoreCase = true) ?: false)
            val matchesStatus = status == null || item.currentCycle?.status == status
            matchesQuery && matchesStatus
        }.sortedWith { a, b ->
            val dateA = a.currentCycle?.dueDate
            val dateB = b.currentCycle?.dueDate
            when {
                dateA == null && dateB == null -> 0
                dateA == null -> 1
                dateB == null -> -1
                sortDesc -> dateB.compareTo(dateA)
                else -> dateA.compareTo(dateB)
            }
        }
        BillListUiState.Success(filtered)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BillListUiState.Loading
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onStatusFilterChange(status: BillStatus?) {
        _selectedStatus.value = status
    }

    fun toggleSortOrder() {
        _sortByDateDesc.value = !_sortByDateDesc.value
    }
}

sealed interface BillListUiState {
    data object Loading : BillListUiState
    data class Success(val bills: List<BillWithCycle>) : BillListUiState
    data class Error(val message: String) : BillListUiState
}
