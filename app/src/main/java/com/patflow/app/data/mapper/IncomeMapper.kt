package com.patflow.app.data.mapper

import com.patflow.app.data.local.entity.IncomeCategoryEntity
import com.patflow.app.data.local.entity.IncomeEntryEntity
import com.patflow.app.data.local.entity.IncomeSourceEntity
import com.patflow.app.domain.model.IncomeCategory
import com.patflow.app.domain.model.IncomeEntry
import com.patflow.app.domain.model.IncomeSource
import com.patflow.app.domain.model.Recurrence
import com.patflow.app.domain.model.RecurrenceType
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun IncomeCategoryEntity.toDomain(): IncomeCategory = IncomeCategory(
    id = id,
    name = name,
    iconKey = iconKey,
    colorHex = colorHex,
    isCustom = isCustom
)

fun IncomeCategory.toEntity(): IncomeCategoryEntity = IncomeCategoryEntity(
    id = id,
    name = name,
    iconKey = iconKey,
    colorHex = colorHex,
    isCustom = isCustom
)

fun IncomeSourceEntity.toDomain(category: IncomeCategory): IncomeSource = IncomeSource(
    id = id,
    category = category,
    name = name,
    defaultAmount = defaultAmount,
    recurrence = Recurrence(
        type = RecurrenceType.valueOf(recurrenceType),
        interval = recurrenceInterval,
        startDate = startDate
    ),
    isActive = isActive,
    isDeleted = isDeleted
)

fun IncomeSource.toEntity(): IncomeSourceEntity = IncomeSourceEntity(
    id = id,
    categoryId = category.id,
    name = name,
    defaultAmount = defaultAmount,
    recurrenceType = recurrence.type.name,
    recurrenceInterval = recurrence.interval,
    startDate = recurrence.startDate,
    endDate = recurrence.endDate,
    isActive = isActive,
    isDeleted = isDeleted
)

fun IncomeEntryEntity.toDomain(category: IncomeCategory): IncomeEntry = IncomeEntry(
    id = id,
    incomeSourceId = incomeSourceId,
    category = category,
    amount = amount,
    currencyCode = currencyCode,
    entryDate = entryDate,
    note = note,
    createdAt = createdAt
)

fun IncomeEntry.toEntity(): IncomeEntryEntity = IncomeEntryEntity(
    id = id,
    incomeSourceId = incomeSourceId,
    categoryId = category.id,
    amount = amount,
    currencyCode = currencyCode,
    entryDate = entryDate,
    note = note,
    createdAt = createdAt
)
