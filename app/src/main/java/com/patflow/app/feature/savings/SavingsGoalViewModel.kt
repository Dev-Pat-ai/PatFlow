package com.patflow.app.feature.savings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.SavingsGoalAnalytics
import com.patflow.app.domain.usecase.savings.GetSavingsGoalAnalyticsUseCase
import com.patflow.app.domain.usecase.savings.GetSavingsGoalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SavingsGoalViewModel @Inject constructor(
    getSavingsGoalsUseCase: GetSavingsGoalsUseCase,
    private val getSavingsGoalAnalyticsUseCase: GetSavingsGoalAnalyticsUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<SavingsUiState> = getSavingsGoalsUseCase()
        .flatMapLatest { goals ->
            if (goals.isEmpty()) {
                flowOf(SavingsUiState.Empty)
            } else {
                combine(goals.map { getSavingsGoalAnalyticsUseCase(it.id) }) { analyticsArray ->
                    SavingsUiState.Success(analyticsArray.filterNotNull())
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SavingsUiState.Loading
        )
}

sealed interface SavingsUiState {
    data object Loading : SavingsUiState
    data object Empty : SavingsUiState
    data class Success(val goals: List<SavingsGoalAnalytics>) : SavingsUiState
    data class Error(val message: String) : SavingsUiState
}
