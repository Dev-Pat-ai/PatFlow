package com.patflow.app.data.mapper

import com.patflow.app.data.local.entity.BillCategoryEntity
import com.patflow.app.data.local.entity.BillCycleEntity
import com.patflow.app.data.local.entity.BillEntity
import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.model.BillCycle
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.Category
import com.patflow.app.domain.model.Recurrence
import com.patflow.app.domain.model.RecurrenceType
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun BillCategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    iconKey = iconKey,
    colorHex = colorHex,
    isCustom = isCustom
)

fun Category.toEntity(): BillCategoryEntity = BillCategoryEntity(
    id = id,
    name = name,
    iconKey = iconKey,
    colorHex = colorHex,
    isCustom = isCustom
)

fun BillEntity.toDomain(category: Category): Bill = Bill(
    id = id,
    name = name,
    category = category,
    defaultAmount = defaultAmount,
    currencyCode = currencyCode,
    accountNumber = accountNumber,
    billReference = billReference,
    merchant = merchant,
    recurrence = Recurrence(
        type = RecurrenceType.valueOf(recurrenceType),
        interval = recurrenceInterval,
        dueDay = dueDay,
        startDate = startDate,
        endDate = endDate
    ),
    notes = notes,
    isActive = isActive,
    isFavorite = isFavorite
)

fun Bill.toEntity(): BillEntity {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return BillEntity(
        id = id,
        categoryId = category.id,
        name = name,
        defaultAmount = defaultAmount,
        currencyCode = currencyCode,
        accountNumber = accountNumber,
        billReference = billReference,
        merchant = merchant,
        recurrenceType = recurrence.type.name,
        recurrenceInterval = recurrence.interval,
        dueDay = recurrence.dueDay,
        startDate = recurrence.startDate,
        endDate = recurrence.endDate,
        isActive = isActive,
        isFavorite = isFavorite,
        notes = notes,
        createdAt = now,
        updatedAt = now
    )
}

fun BillCycleEntity.toDomain(): BillCycle = BillCycle(
    id = id,
    billId = billId,
    periodStart = periodStart,
    dueDate = dueDate,
    amountDue = amountDue,
    amountPaid = amountPaid,
    status = BillStatus.valueOf(status)
)

fun BillCycle.toEntity(): BillCycleEntity {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return BillCycleEntity(
        id = id,
        billId = billId,
        periodStart = periodStart,
        dueDate = dueDate,
        amountDue = amountDue,
        amountPaid = amountPaid,
        status = status.name,
        createdAt = now,
        updatedAt = now
    )
}
