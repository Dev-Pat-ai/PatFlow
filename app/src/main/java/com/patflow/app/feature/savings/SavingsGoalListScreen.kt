package com.patflow.app.feature.savings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import com.patflow.app.core.theme.PatFlowSpacing

@Composable
fun SavingsGoalListScreen(
    onAddGoalClick: () -> Unit,
    onGoalClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SavingsGoalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            SavingsUiState.Loading -> LoadingState()
            SavingsUiState.Empty -> EmptyState(
                title = "No savings goals",
                description = "Start saving for something big!",
                icon = Icons.Rounded.Savings,
                action = {
                    AppButton(onClick = onAddGoalClick) {
                        Text("Create Goal")
                    }
                }
            )
            is SavingsUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(PatFlowSpacing.space5),
                    verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
                ) {
                    items(state.goals) { analytics ->
                        SavingsGoalProgressCard(
                            name = analytics.goal.name,
                            targetAmount = analytics.goal.targetAmount,
                            currentAmount = analytics.goal.currentAmount,
                            percentageUsed = analytics.progressPercentage,
                            currencyCode = analytics.goal.currencyCode,
                            onClick = { onGoalClick(analytics.goal.id) }
                        )
                    }
                }
            }
            is SavingsUiState.Error -> FullScreenError(title = "Error", description = state.message)
        }
    }
}
