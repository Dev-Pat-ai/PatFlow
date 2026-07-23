package com.patflow.app.feature.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.ReportData
import com.patflow.app.domain.model.ReportFilter
import com.patflow.app.domain.model.UserPreferences
import com.patflow.app.domain.usecase.report.GetReportDataUseCase
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

/**
 * ViewModel for the Reports & Analytics screen.
 * Handles report filter state and analytics data orchestration.
 */
@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getReportDataUseCase: GetReportDataUseCase,
    private val getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {

    private val _filter = MutableStateFlow<ReportFilter>(ReportFilter.ThisMonth)
    val filter: StateFlow<ReportFilter> = _filter.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ReportsUiState> = _filter
        .flatMapLatest { currentFilter ->
            getReportDataUseCase(currentFilter)
                .map<ReportData, ReportsUiState> { data ->
                    ReportsUiState.Success(data)
                }
                .catch { e ->
                    emit(ReportsUiState.Error(e.message ?: "An unexpected error occurred"))
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ReportsUiState.Loading
        )

    val userPreferences: StateFlow<UserPreferences?> = getUserSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    /**
     * Updates the active report filter.
     */
    fun onFilterChange(newFilter: ReportFilter) {
        _filter.value = newFilter
    }

    /**
     * Sets a custom date range for the report.
     */
    fun onCustomDateRangeSelected(start: LocalDate, end: LocalDate) {
        _filter.value = ReportFilter.Custom(start, end)
    }

    /**
     * Triggers a manual refresh of the report data.
     */
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            kotlinx.coroutines.delay(1000L) // Simulated refresh
            _isRefreshing.value = false
        }
    }
}

sealed interface ReportsUiState {
    data object Loading : ReportsUiState
    data class Success(val data: ReportData) : ReportsUiState
    data class Error(val message: String) : ReportsUiState
}
