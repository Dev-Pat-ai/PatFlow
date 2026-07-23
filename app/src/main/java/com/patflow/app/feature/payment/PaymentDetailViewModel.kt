package com.patflow.app.feature.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.PaymentHistory
import com.patflow.app.domain.usecase.payment.GetPaymentDetailUseCase
import com.patflow.app.domain.usecase.payment.UndoPaymentUseCase
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

/**
 * ViewModel for the Payment Detail screen.
 * Handles single transaction retrieval and the undo flow.
 */
@HiltViewModel
class PaymentDetailViewModel @Inject constructor(
    getPaymentDetailUseCase: GetPaymentDetailUseCase,
    private val undoPaymentUseCase: UndoPaymentUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val paymentId: Long = checkNotNull(savedStateHandle["paymentId"]) { "paymentId is required" }.toString().toLong()

    val uiState: StateFlow<PaymentDetailUiState> = getPaymentDetailUseCase(paymentId)
        .map { history ->
            if (history == null) PaymentDetailUiState.Loading
            else PaymentDetailUiState.Success(history)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PaymentDetailUiState.Loading
        )

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun undoPayment() {
        viewModelScope.launch {
            undoPaymentUseCase(paymentId)
            _eventFlow.emit(UiEvent.UndoSuccess)
        }
    }

    sealed interface UiEvent {
        data object UndoSuccess : UiEvent
    }
}

sealed interface PaymentDetailUiState {
    data object Loading : PaymentDetailUiState
    data class Success(val history: PaymentHistory) : PaymentDetailUiState
    data class Error(val message: String) : PaymentDetailUiState
}
