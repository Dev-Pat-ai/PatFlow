package com.patflow.app.feature.budget

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.BudgetAnalytics
import com.patflow.app.domain.usecase.budget.ArchiveBudgetUseCase
import com.patflow.app.domain.usecase.budget.DeleteBudgetUseCase
import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetDetailViewModel @Inject constructor(
    private val getBudgetAnalyticsUseCase: GetBudgetAnalyticsUseCase,
    private val deleteBudgetUseCase: DeleteBudgetUseCase,
    private val archiveBudgetUseCase: ArchiveBudgetUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val budgetId: Long = checkNotNull(savedStateHandle["budgetId"]) { "budgetId is required" }.toString().toLong()

    val uiState: StateFlow<BudgetDetailUiState> = getBudgetAnalyticsUseCase(budgetId)
        .map { analytics ->
            if (analytics == null) BudgetDetailUiState.Loading
            else BudgetDetailUiState.Success(analytics)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BudgetDetailUiState.Loading
        )

    fun deleteBudget() {
        viewModelScope.launch {
            deleteBudgetUseCase(budgetId)
        }
    }

    fun toggleArchive(archived: Boolean) {
        viewModelScope.launch {
            archiveBudgetUseCase(budgetId, archived)
        }
    }
}

sealed interface BudgetDetailUiState {
    data object Loading : BudgetDetailUiState
    data class Success(val analytics: BudgetAnalytics) : BudgetDetailUiState
    data class Error(val message: String) : BudgetDetailUiState
}
