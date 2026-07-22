package com.patflow.app.feature.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.CategoryChip
import com.patflow.app.core.components.CategoryType
import com.patflow.app.core.components.ConfirmationDialog
import com.patflow.app.core.components.SectionHeader
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.core.utils.rememberHapticFeedbackController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDetailScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PaymentDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val haptic = rememberHapticFeedbackController()
    var showUndoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                PaymentDetailViewModel.UiEvent.UndoSuccess -> {
                    haptic.confirm()
                    onNavigateBack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Payment Details",
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (val state = uiState) {
            PaymentDetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
                }
            }
            is PaymentDetailUiState.Success -> {
                val history = state.history
                val payment = history.payment
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(PatFlowSpacing.space4),
                    verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = history.billName,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = CurrencyFormatter.formatAmount(payment.amount, payment.currencyCode),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    SectionHeader(title = "Information")
                    DetailRow("Category") {
                        CategoryChip(category = mapCategoryToType(history.category.name))
                    }
                    DetailRow("Date", value = payment.paymentDate.toString())
                    DetailRow("Method", value = payment.method.name)
                    
                    if (!payment.note.isNullOrBlank()) {
                        SectionHeader(title = "Notes")
                        Text(text = payment.note)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    AppButton(
                        onClick = { showUndoDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Undo Payment")
                    }
                }
            }
            is PaymentDetailUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    Text(text = "Error: ${state.message}", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

    if (showUndoDialog) {
        ConfirmationDialog(
            onDismissRequest = { showUndoDialog = false },
            onConfirm = {
                showUndoDialog = false
                viewModel.undoPayment()
            },
            title = "Undo Payment?",
            text = "This will remove the payment and revert the bill's status."
        )
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String? = null,
    content: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        if (content != null) content()
        else if (value != null) Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

private fun mapCategoryToType(name: String): CategoryType {
    return try {
        CategoryType.valueOf(name.uppercase().replace(" ", "_"))
    } catch (_: Exception) {
        CategoryType.ELECTRICITY
    }
}
