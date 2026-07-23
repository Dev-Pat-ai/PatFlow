package com.patflow.app.feature.bills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppButton
import com.patflow.app.core.components.AppSnackbarHost
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.CategoryChip
import com.patflow.app.core.components.CategoryType
import com.patflow.app.core.components.DeleteConfirmationDialog
import com.patflow.app.core.components.SectionHeader
import com.patflow.app.core.components.StatusChip
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CategoryMapper
import com.patflow.app.core.utils.CurrencyFormatter

import com.patflow.app.core.utils.rememberHapticFeedbackController

/**
 * Screen for viewing bill details (Architecture §6).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailScreen(
    onNavigateBack: () -> Unit,
    onEditClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BillDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val haptic = rememberHapticFeedbackController()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is BillDetailViewModel.UiEvent.ActionSuccess -> {
                    haptic.confirm()
                    snackbarHostState.showSnackbar(event.message)
                }
                BillDetailViewModel.UiEvent.DeleteSuccess -> {
                    haptic.confirm()
                    onNavigateBack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Bill Details",
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val state = uiState
                    if (state is BillDetailUiState.Success) {
                        IconButton(onClick = { onEditClick(state.detail.bill.id) }) {
                            Icon(Icons.Rounded.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Rounded.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
        },
        snackbarHost = { AppSnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        when (val state = uiState) {
            BillDetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
                }
            }
            is BillDetailUiState.Success -> {
                val detail = state.detail
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(PatFlowSpacing.space4),
                    verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
                ) {
                    DetailHeroSection(detail)
                    
                    SectionHeader(title = "Information")
                    DetailInfoRow("Category", content = {
                        CategoryChip(category = CategoryMapper.mapToType(detail.bill.category.name))
                    })
                    DetailInfoRow("Recurrence", value = detail.bill.recurrence.type.name)
                    
                    if (!detail.bill.notes.isNullOrBlank()) {
                        SectionHeader(title = "Notes")
                        Text(
                            text = detail.bill.notes,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    val currentCycle = detail.cycles.firstOrNull()
                    if (currentCycle != null && currentCycle.status != com.patflow.app.domain.model.BillStatus.PAID) {
                        AppButton(
                            onClick = { viewModel.markAsPaid(currentCycle.amountDue - currentCycle.amountPaid) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Mark as Paid")
                        }
                    }
                }
            }
            is BillDetailUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    Text(text = "Error: ${state.message}", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { showDeleteDialog = false },
            onDelete = {
                showDeleteDialog = false
                viewModel.deleteBill()
            },
            title = "Delete Bill?",
            text = "This will permanently remove the bill and all its payment history."
        )
    }
}

@Composable
private fun DetailHeroSection(detail: com.patflow.app.domain.usecase.bill.BillDetail) {
    val currentCycle = detail.cycles.firstOrNull()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = detail.bill.name,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = CurrencyFormatter.formatAmount(currentCycle?.amountDue ?: detail.bill.defaultAmount),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
        if (currentCycle != null) {
            StatusChip(status = currentCycle.status)
        }
    }
}

@Composable
private fun DetailInfoRow(
    label: String,
    value: String? = null,
    content: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (content != null) {
            content()
        } else if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
