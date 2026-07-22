package com.patflow.app.feature.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.ReportData
import com.patflow.app.domain.model.ReportFilter
import com.patflow.app.domain.usecase.report.GetReportDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val getReportDataUseCase: GetReportDataUseCase
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

    fun onFilterChange(newFilter: ReportFilter) {
        _filter.value = newFilter
    }

    fun onCustomDateRangeSelected(start: LocalDate, end: LocalDate) {
        _filter.value = ReportFilter.Custom(start, end)
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            kotlinx.coroutines.delay(1000) // Simulated refresh
            _isRefreshing.value = false
        }
    }
}

sealed interface ReportsUiState {
    data object Loading : ReportsUiState
    data class Success(val data: ReportData) : ReportsUiState
    data class Error(val message: String) : ReportsUiState
}
