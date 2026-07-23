package com.patflow.app.domain.model

import kotlinx.datetime.LocalDate

/**
 * Domain model for a recurring bill template (Architecture §8.3 / FR-1.1).
 * Separate from [BillCycle] which represents a specific instance.
 */
data class Bill(
    val id: Long = 0,
    val name: String,
    val category: Category,
    val defaultAmount: Double,
    val currencyCode: String = "PHP",
    val merchant: String? = null,
    val recurrence: Recurrence,
    val notes: String? = null,
    val isActive: Boolean = true,
    val isFavorite: Boolean = false
)

/**
 * Domain model for recurrence rules (Architecture §1.1).
 */
data class Recurrence(
    val type: RecurrenceType,
    val interval: Int = 1,
    val dueDay: Int? = null,
    val startDate: LocalDate,
    val endDate: LocalDate? = null
)
