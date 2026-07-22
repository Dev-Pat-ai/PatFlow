package com.patflow.app.domain.model

import kotlinx.datetime.LocalDate

/**
 * Domain model for a specific billing period instance.
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
 * Composite model for the list view — a bill and its current active cycle.
 */
data class BillWithCycle(
    val bill: Bill,
    val currentCycle: BillCycle?
)
