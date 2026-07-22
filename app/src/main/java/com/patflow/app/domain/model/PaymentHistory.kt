package com.patflow.app.domain.model

/**
 * Composite model for history list — a payment and the bill it belongs to.
 */
data class PaymentHistory(
    val payment: Payment,
    val billName: String,
    val category: Category
)
