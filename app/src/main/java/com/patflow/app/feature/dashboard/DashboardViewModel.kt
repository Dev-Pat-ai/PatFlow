package com.patflow.app.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.DashboardData
import com.patflow.app.domain.usecase.dashboard.GetDashboardDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardDataUseCase: GetDashboardDataUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    val uiState: StateFlow<DashboardUiState> = getDashboardDataUseCase()
        .map<DashboardData, DashboardUiState> { data ->
            if (data.totalBillsThisMonth == 0.0 && data.recentPayments.isEmpty()) {
                DashboardUiState.Empty
            } else {
                DashboardUiState.Success(data)
            }
        }
        .onStart { 
            // Delay emit slightly to show loading if needed, or rely on initialValue
        }
        .catch { e ->
            emit(DashboardUiState.Error(e.message ?: "An unexpected error occurred"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardUiState.Loading
        )

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // Data will automatically update via Flow, we just simulate a refresh trigger
            // if we had a manual refresh mechanism or remote source.
            // For offline-first with Flow, this is mostly for UI feedback.
            kotlinx.coroutines.delay(1000)
            _isRefreshing.value = false
        }
    }
}

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data object Empty : DashboardUiState
    data class Success(val data: DashboardData) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}
