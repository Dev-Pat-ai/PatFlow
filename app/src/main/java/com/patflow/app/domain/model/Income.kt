package com.patflow.app.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * Domain model for income categories (Architecture §8.4).
 */
data class IncomeCategory(
    val id: Long = 0,
    val name: String,
    val iconKey: String,
    val colorHex: String,
    val isCustom: Boolean = false
)

/**
 * Domain model for a recurring income source (Architecture §8.4).
 * Mirroring the bill template pattern.
 */
data class IncomeSource(
    val id: Long = 0,
    val category: IncomeCategory,
    val name: String,
    val defaultAmount: Double,
    val recurrence: Recurrence,
    val isActive: Boolean = true,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false
)

/**
 * Domain model representing a single logged income entry (Architecture §8.4 / FR-12.2).
 */
data class IncomeEntry(
    val id: Long = 0,
    val incomeSourceId: Long? = null,
    val category: IncomeCategory,
    val amount: Double,
    val currencyCode: String = "PHP",
    val entryDate: LocalDate,
    val note: String? = null,
    val createdAt: LocalDateTime
)

/**
 * Composite model for income history list — an entry and its associated details.
 */
data class IncomeWithDetails(
    val entry: IncomeEntry,
    val sourceName: String? = null
)
