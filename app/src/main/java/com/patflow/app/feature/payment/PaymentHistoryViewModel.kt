package com.patflow.app.feature.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.PaymentHistory
import com.patflow.app.domain.usecase.payment.GetPaymentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the Payment History screen.
 * Manages searching and filtering of transactions.
 */
@HiltViewModel
class PaymentHistoryViewModel @Inject constructor(
    getPaymentsUseCase: GetPaymentsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val uiState: StateFlow<PaymentHistoryUiState> = combine(
        getPaymentsUseCase(),
        _searchQuery
    ) { payments, query ->
        val filtered = if (query.isBlank()) {
            payments
        } else {
            payments.filter { 
                it.billName.contains(query, ignoreCase = true) ||
                (it.payment.note?.contains(query, ignoreCase = true) ?: false)
            }
        }
        PaymentHistoryUiState.Success(filtered)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PaymentHistoryUiState.Loading
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}

sealed interface PaymentHistoryUiState {
    data object Loading : PaymentHistoryUiState
    data class Success(val payments: List<PaymentHistory>) : PaymentHistoryUiState
    data class Error(val message: String) : PaymentHistoryUiState
}
