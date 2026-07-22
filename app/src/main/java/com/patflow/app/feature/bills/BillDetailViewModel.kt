package com.patflow.app.feature.bills

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.PaymentMethod
import com.patflow.app.domain.usecase.bill.BillDetail
import com.patflow.app.domain.usecase.bill.DeleteBillUseCase
import com.patflow.app.domain.usecase.bill.GetBillDetailUseCase
import com.patflow.app.domain.usecase.bill.MarkBillAsPaidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillDetailViewModel @Inject constructor(
    getBillDetailUseCase: GetBillDetailUseCase,
    private val deleteBillUseCase: DeleteBillUseCase,
    private val markBillAsPaidUseCase: MarkBillAsPaidUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val billId: Long = checkNotNull(savedStateHandle["billId"]) { "billId is required" }.toString().toLong()

    val uiState: StateFlow<BillDetailUiState> = getBillDetailUseCase(billId)
        .map { detail ->
            if (detail == null) BillDetailUiState.Loading
            else BillDetailUiState.Success(detail)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BillDetailUiState.Loading
        )

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun markAsPaid(amount: Double) {
        viewModelScope.launch {
            val currentState = uiState.value
            if (currentState is BillDetailUiState.Success) {
                val cycleId = currentState.detail.cycles.firstOrNull()?.id ?: return@launch
                markBillAsPaidUseCase(cycleId, amount, PaymentMethod.OTHER)
                _eventFlow.emit(UiEvent.ActionSuccess("Bill marked as paid"))
            }
        }
    }

    fun deleteBill() {
        viewModelScope.launch {
            deleteBillUseCase(billId)
            _eventFlow.emit(UiEvent.DeleteSuccess)
        }
    }

    sealed interface UiEvent {
        data class ActionSuccess(val message: String) : UiEvent
        data object DeleteSuccess : UiEvent
    }
}

sealed interface BillDetailUiState {
    data object Loading : BillDetailUiState
    data class Success(val detail: BillDetail) : BillDetailUiState
    data class Error(val message: String) : BillDetailUiState
}
