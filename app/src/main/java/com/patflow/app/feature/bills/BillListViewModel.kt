package com.patflow.app.feature.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.model.PaymentMethod
import com.patflow.app.domain.usecase.bill.DeleteBillUseCase
import com.patflow.app.domain.usecase.bill.GetBillsUseCase
import com.patflow.app.domain.usecase.bill.MarkBillAsPaidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Bill List screen.
 * Manages searching, filtering, and multi-select logic for bills.
 */
@HiltViewModel
class BillListViewModel @Inject constructor(
    getBillsUseCase: GetBillsUseCase,
    private val deleteBillUseCase: DeleteBillUseCase,
    private val markBillAsPaidUseCase: MarkBillAsPaidUseCase,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedStatus = MutableStateFlow<BillStatus?>(null)
    val selectedStatus: StateFlow<BillStatus?> = _selectedStatus

    private val _sortByDateDesc = MutableStateFlow(value = false)
    val sortByDateDesc: StateFlow<Boolean> = _sortByDateDesc

    private val _selectedIds = MutableStateFlow<Set<Long>>(emptySet())
    val selectedIds: StateFlow<Set<Long>> = _selectedIds

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    val uiState: StateFlow<BillListUiState> = combine(
        getBillsUseCase(),
        _searchQuery,
        _selectedStatus,
        _sortByDateDesc,
        _selectedIds
    ) { bills, query, status, sortDesc, selectedIds ->
        val filtered = bills.filter { item ->
            val matchesQuery = item.bill.name.contains(query, ignoreCase = true) ||
                    (item.bill.merchant?.contains(query, ignoreCase = true) ?: false)
            val matchesStatus = (status == null) || (item.currentCycle?.status == status)
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
        BillListUiState.Success(filtered, selectedIds)
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

    fun toggleSelection(billId: Long) {
        _selectedIds.value = if (_selectedIds.value.contains(billId)) {
            _selectedIds.value - billId
        } else {
            _selectedIds.value + billId
        }
    }

    fun clearSelection() {
        _selectedIds.value = emptySet()
    }

    fun deleteSelectedBills() {
        viewModelScope.launch {
            val idsToDelete = _selectedIds.value
            idsToDelete.forEach { deleteBillUseCase(it) }
            clearSelection()
            _eventFlow.emit(UiEvent.ActionSuccess("Deleted ${idsToDelete.size} bills"))
        }
    }

    fun markSelectedAsPaid() {
        viewModelScope.launch {
            val state = uiState.value
            if (state is BillListUiState.Success) {
                val selectedItems = state.bills.filter { _selectedIds.value.contains(it.bill.id) }
                selectedItems.forEach { item ->
                    item.currentCycle?.let { cycle ->
                        if (cycle.status != BillStatus.PAID) {
                            markBillAsPaidUseCase(cycle.id, cycle.amountDue - cycle.amountPaid, PaymentMethod.OTHER)
                        }
                    }
                }
                clearSelection()
                _eventFlow.emit(UiEvent.ActionSuccess("Marked ${selectedItems.size} bills as paid"))
            }
        }
    }

    fun markAsPaid(billId: Long) {
        viewModelScope.launch {
            val state = uiState.value
            if (state is BillListUiState.Success) {
                val item = state.bills.find { it.bill.id == billId }
                item?.currentCycle?.let { cycle ->
                    markBillAsPaidUseCase(cycle.id, cycle.amountDue - cycle.amountPaid, PaymentMethod.OTHER)
                    _eventFlow.emit(UiEvent.ActionSuccess("${item.bill.name} marked as paid"))
                }
            }
        }
    }

    sealed interface UiEvent {
        data class ActionSuccess(val message: String) : UiEvent
    }
}

sealed interface BillListUiState {
    data object Loading : BillListUiState
    data class Success(
        val bills: List<BillWithCycle>,
        val selectedIds: Set<Long> = emptySet()
    ) : BillListUiState
    data class Error(val message: String) : BillListUiState
}
