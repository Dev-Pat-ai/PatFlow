package com.patflow.app.data.mapper

import com.patflow.app.data.local.entity.*
import com.patflow.app.domain.model.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun BillCategoryEntity.toBackup(): CategoryBackup = CategoryBackup(id, name, iconKey, colorHex, isCustom)
fun BillEntity.toBackup(): BillBackup = BillBackup(
    id, categoryId, name, defaultAmount, currencyCode, merchant, recurrenceType,
    recurrenceInterval, dueDay, startDate.toString(), endDate?.toString(), isActive, isFavorite, notes
)
fun BillCycleEntity.toBackup(): BillCycleBackup = BillCycleBackup(
    id, billId, periodStart.toString(), dueDate.toString(), amountDue, amountPaid, status
)
fun PaymentEntity.toBackup(): PaymentBackup = PaymentBackup(
    id, billCycleId, amount, paymentDate.toString(), method, note
)

fun IncomeCategoryEntity.toBackup(): IncomeCategoryBackup = IncomeCategoryBackup(id, name, iconKey, colorHex, isCustom)
fun IncomeSourceEntity.toBackup(): IncomeSourceBackup = IncomeSourceBackup(
    id, categoryId, name, defaultAmount, recurrenceType, recurrenceInterval, startDate.toString(), endDate?.toString(), isActive, isDeleted
)
fun IncomeEntryEntity.toBackup(): IncomeEntryBackup = IncomeEntryBackup(
    id, incomeSourceId, categoryId, amount, currencyCode, entryDate.toString(), note, createdAt.toString()
)

fun CategoryBackup.toEntity(): BillCategoryEntity = BillCategoryEntity(id, name, iconKey, colorHex, isCustom)
fun BillBackup.toEntity(): BillEntity = BillEntity(
    id = id, categoryId = categoryId, name = name, defaultAmount = defaultAmount, currencyCode = currencyCode,
    merchant = merchant, recurrenceType = recurrenceType, recurrenceInterval = recurrenceInterval,
    dueDay = dueDay, startDate = kotlinx.datetime.LocalDate.parse(startDate),
    endDate = endDate?.let { kotlinx.datetime.LocalDate.parse(it) }, isActive = isActive, isFavorite = isFavorite,
    notes = notes, createdAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    updatedAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
)
fun BillCycleBackup.toEntity(): BillCycleEntity = BillCycleEntity(
    id = id, billId = billId, periodStart = kotlinx.datetime.LocalDate.parse(periodStart),
    dueDate = kotlinx.datetime.LocalDate.parse(dueDate), amountDue = amountDue, amountPaid = amountPaid,
    status = status, createdAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    updatedAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
)
fun PaymentBackup.toEntity(): PaymentEntity = PaymentEntity(
    id = id, billCycleId = billCycleId, amount = amount, paymentDate = kotlinx.datetime.LocalDate.parse(paymentDate),
    method = method, note = note, createdAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
)
