package com.patflow.app.feature.payment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patflow.app.core.components.AppTextField
import com.patflow.app.core.components.AppTopBar
import com.patflow.app.core.components.CategoryChip
import com.patflow.app.core.components.CategoryType
import com.patflow.app.core.components.EmptyState
import com.patflow.app.core.components.SkeletonBox
import com.patflow.app.core.theme.PatFlowSpacing
import com.patflow.app.core.utils.CurrencyFormatter
import com.patflow.app.domain.model.PaymentHistory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentHistoryScreen(
    onNavigateBack: () -> Unit,
    onPaymentClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PaymentHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Payment History",
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AppTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                placeholder = "Search transactions...",
                modifier = Modifier.padding(PatFlowSpacing.space4)
            )

            when (val state = uiState) {
                PaymentHistoryUiState.Loading -> {
                    PaymentHistoryLoading()
                }
                is PaymentHistoryUiState.Success -> {
                    if (state.payments.isEmpty()) {
                        EmptyState(
                            title = "No transactions",
                            description = "Your payment history will appear here.",
                            icon = Icons.AutoMirrored.Rounded.ReceiptLong
                        )
                    } else {
                        PaymentList(
                            payments = state.payments,
                            onPaymentClick = onPaymentClick
                        )
                    }
                }
                is PaymentHistoryUiState.Error -> {
                    Text(text = "Error: ${state.message}", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
private fun PaymentList(
    payments: List<PaymentHistory>,
    onPaymentClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        items(payments, key = { it.payment.id }) { item ->
            PaymentItem(
                history = item,
                onClick = { onPaymentClick(item.payment.id) }
            )
        }
    }
}

@Composable
private fun PaymentItem(
    history: PaymentHistory,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = PatFlowSpacing.space2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PatFlowSpacing.space4)
    ) {
        CategoryChip(category = mapCategoryToType(history.category.name))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = history.billName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${history.payment.paymentDate} · ${history.payment.method.name}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = CurrencyFormatter.formatAmount(history.payment.amount, history.payment.currencyCode),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun PaymentHistoryLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PatFlowSpacing.space4),
        verticalArrangement = Arrangement.spacedBy(PatFlowSpacing.space3)
    ) {
        repeat(5) {
            SkeletonBox(height = 60.dp)
        }
    }
}

private fun mapCategoryToType(name: String): CategoryType {
    return try {
        CategoryType.valueOf(name.uppercase().replace(" ", "_"))
    } catch (_: Exception) {
        CategoryType.ELECTRICITY
    }
}
