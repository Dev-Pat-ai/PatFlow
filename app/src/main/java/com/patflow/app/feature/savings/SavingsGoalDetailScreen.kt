package com.patflow.app.feature.savings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.*
import com.patflow.app.core.components.TopBarType
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CurrencyFormatter

import com.patflow.app.core.utils.rememberHapticFeedbackController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingsGoalDetailScreen(
    onNavigateBack: () -> Unit,
    onEditClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SavingsGoalDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val haptic = rememberHapticFeedbackController()
    var showContributionDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Goal Details",
                type = TopBarType.Small,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (uiState is SavingsDetailUiState.Success) {
                        val goal = (uiState as SavingsDetailUiState.Success).analytics.goal
                        IconButton(onClick = { 
                            viewModel.deleteGoal()
                            onNavigateBack()
                        }) {
                            Icon(Icons.Rounded.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                        }
                        IconButton(onClick = { onEditClick(goal.id) }) {
                            Icon(Icons.Rounded.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            AppFab(
                onClick = { showContributionDialog = true },
                icon = Icons.Rounded.Add,
                contentDescription = "Contribute"
            )
        }
    ) { padding ->
        Box(modifier = modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                SavingsDetailUiState.Loading -> LoadingState()
                is SavingsDetailUiState.Success -> {
                    val analytics = state.analytics
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(PatFlowSpacing.space5),
                        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space5)
                    ) {
                        // 1. Progress Overview
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
                        ) {
                            Text(
                                text = analytics.goal.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                            
                            LinearProgressIndicator(
                                progress = { analytics.progressPercentage.coerceIn(0f, 1f) },
                                modifier = Modifier.fillMaxWidth().height(16.dp),
                                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                            )
                            
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "${(analytics.progressPercentage * 100).toInt()}% Complete",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1
                                )
                                Text(
                                    text = CurrencyFormatter.formatAmount(analytics.goal.targetAmount),
                                    style = MaterialTheme.typography.labelLarge,
                                    maxLines = 1
                                )
                            }
                        }

                        // 2. Stats
                        Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                            SectionHeader(title = "Analytics")
                            StatItem(label = "Current Savings", value = CurrencyFormatter.formatAmount(analytics.goal.currentAmount))
                            StatItem(label = "Remaining", value = CurrencyFormatter.formatAmount(analytics.remainingAmount))
                            analytics.monthlyRequiredSavings?.let {
                                StatItem(label = "Monthly Required", value = CurrencyFormatter.formatAmount(it), subtitle = "To reach target by ${analytics.goal.targetDate}")
                            }
                        }

                        // 3. Info
                        Column(verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)) {
                            SectionHeader(title = "Information")
                            analytics.goal.targetDate?.let { StatItem(label = "Target Date", value = it.toString()) }
                            analytics.goal.notes?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
                        }
                    }
                }
                is SavingsDetailUiState.Error -> FullScreenError(title = "Error", description = state.message)
            }
        }
    }

    if (showContributionDialog) {
        ContributionDialog(
            onDismiss = { showContributionDialog = false },
            onConfirm = { amount, note ->
                haptic.confirm()
                viewModel.addContribution(amount, note)
                showContributionDialog = false
            }
        )
    }
}

@Composable
private fun StatItem(label: String, value: String, subtitle: String? = null) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (subtitle != null) Text(text = subtitle, style = MaterialTheme.typography.labelSmall)
        }
        Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ContributionDialog(onDismiss: () -> Unit, onConfirm: (Double, String?) -> Unit) {
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Contribution") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AmountTextField(value = amount, onValueChange = { amount = it }, label = "Amount")
                AppTextField(value = note, onValueChange = { note = it }, label = "Note (Optional)")
            }
        },
        confirmButton = {
            TextButton(onClick = { amount.toDoubleOrNull()?.let { onConfirm(it, note.ifBlank { null }) } }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
