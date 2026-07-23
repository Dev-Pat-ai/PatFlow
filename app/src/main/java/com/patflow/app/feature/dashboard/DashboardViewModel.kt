package com.patflow.app.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.DashboardData
import com.patflow.app.domain.model.UserPreferences
import com.patflow.app.domain.usecase.dashboard.GetDashboardDataUseCase
import com.patflow.app.domain.usecase.insights.GetSmartInsightsUseCase
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Dashboard (Home) screen.
 * Orchestrates the aggregation of stats, trends, and upcoming activities.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardDataUseCase: GetDashboardDataUseCase,
    private val getSmartInsightsUseCase: GetSmartInsightsUseCase,
    getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    val userPreferences: StateFlow<UserPreferences?> = getUserSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val uiState: StateFlow<DashboardUiState> = combine(
        getDashboardDataUseCase(),
        getSmartInsightsUseCase()
    ) { data, smartInsights ->
        val combinedInsights = (data.insights + smartInsights.map { "${it.title}: ${it.message}" }).distinct()
        val successData = data.copy(insights = combinedInsights)
        
        if (successData.totalBillsThisMonth == 0.0 && successData.upcomingBills.isEmpty() && successData.recentPayments.isEmpty()) {
            DashboardUiState.Empty
        } else {
            DashboardUiState.Success(successData)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState.Loading
    )

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // WorkManager jobs are periodic, manual refresh just triggers state re-emission
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
