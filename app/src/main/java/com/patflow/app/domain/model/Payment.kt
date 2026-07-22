package com.patflow.app.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * Domain model representing a single payment (Architecture §8.3 / FR-2.1).
 * Designed for future-proofing: supports partial payments and optional attachments.
 */
data class Payment(
    val id: Long = 0,
    val billCycleId: Long,
    val amount: Double,
    val currencyCode: String = "PHP",
    val paymentDate: LocalDate,
    val method: PaymentMethod,
    val note: String? = null,
    val referenceNumber: String? = null,
    val attachmentUrl: String? = null, // Future-proofing: attachment support
    val createdAt: LocalDateTime
)
