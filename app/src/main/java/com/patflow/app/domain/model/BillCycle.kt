package com.patflow.app.domain.model

import kotlinx.datetime.LocalDate

/**
 * Domain model for a specific billing period instance (Architecture §8.3 / FR-1.6).
 * Tracks actual due amount, paid amount, and derived status.
 */
data class BillCycle(
    val id: Long = 0,
    val billId: Long,
    val periodStart: LocalDate,
    val dueDate: LocalDate,
    val amountDue: Double,
    val amountPaid: Double = 0.0,
    val status: BillStatus
)

/**
 * Composite model for list views — a bill and its current active cycle (Architecture §7).
 */
data class BillWithCycle(
    val bill: Bill,
    val currentCycle: BillCycle?
)
