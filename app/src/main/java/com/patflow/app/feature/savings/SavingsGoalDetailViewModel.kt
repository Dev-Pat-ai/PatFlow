package com.patflow.app.feature.savings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.SavingsContribution
import com.patflow.app.domain.model.SavingsGoalAnalytics
import com.patflow.app.domain.usecase.savings.AddSavingsContributionUseCase
import com.patflow.app.domain.usecase.savings.DeleteSavingsGoalUseCase
import com.patflow.app.domain.usecase.savings.GetSavingsGoalAnalyticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class SavingsGoalDetailViewModel @Inject constructor(
    private val getGoalAnalyticsUseCase: GetSavingsGoalAnalyticsUseCase,
    private val addContributionUseCase: AddSavingsContributionUseCase,
    private val deleteGoalUseCase: DeleteSavingsGoalUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val goalId: Long = checkNotNull(savedStateHandle["goalId"]) { "goalId is required" }.toString().toLong()

    val uiState: StateFlow<SavingsDetailUiState> = getGoalAnalyticsUseCase(goalId)
        .map { analytics ->
            if (analytics == null) SavingsDetailUiState.Loading
            else SavingsDetailUiState.Success(analytics)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SavingsDetailUiState.Loading
        )

    fun addContribution(amount: Double, note: String?) {
        viewModelScope.launch {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            addContributionUseCase(
                SavingsContribution(
                    savingsGoalId = goalId,
                    amount = amount,
                    contributionDate = now.date,
                    note = note,
                    createdAt = now
                )
            )
        }
    }

    fun deleteGoal() {
        viewModelScope.launch {
            deleteGoalUseCase(goalId)
        }
    }
}

sealed interface SavingsDetailUiState {
    data object Loading : SavingsDetailUiState
    data class Success(val analytics: SavingsGoalAnalytics) : SavingsDetailUiState
    data class Error(val message: String) : SavingsDetailUiState
}
