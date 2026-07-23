package com.patflow.app.feature.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.BudgetAnalytics
import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase
import com.patflow.app.domain.usecase.budget.GetBudgetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    getBudgetsUseCase: GetBudgetsUseCase,
    private val getBudgetAnalyticsUseCase: GetBudgetAnalyticsUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<BudgetUiState> = getBudgetsUseCase()
        .flatMapLatest { budgets ->
            if (budgets.isEmpty()) {
                flowOf(BudgetUiState.Empty)
            } else {
                // Combine analytics for each budget
                combine(budgets.map { getBudgetAnalyticsUseCase(it.id) }) { analyticsArray ->
                    BudgetUiState.Success(analyticsArray.filterNotNull())
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BudgetUiState.Loading
        )
}

sealed interface BudgetUiState {
    data object Loading : BudgetUiState
    data object Empty : BudgetUiState
    data class Success(val budgets: List<BudgetAnalytics>) : BudgetUiState
    data class Error(val message: String) : BudgetUiState
}
