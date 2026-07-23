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

fun BudgetEntity.toBackup(): BudgetBackup = BudgetBackup(
    id, name, type, totalAmount, currencyCode, startDate.toString(), endDate.toString(), isActive, isArchived
)
fun BudgetCategoryLimitEntity.toBackup(): BudgetLimitBackup = BudgetLimitBackup(id, budgetId, categoryId, limitAmount)

fun SavingsGoalEntity.toBackup(): SavingsGoalBackup = SavingsGoalBackup(
    id, name, targetAmount, currencyCode, currentAmount, targetDate?.toString(), iconKey, colorHex, notes, priority, isCompleted, isArchived
)
fun SavingsContributionEntity.toBackup(): SavingsContributionBackup = SavingsContributionBackup(
    id, savingsGoalId, amount, contributionDate.toString(), note
)

fun CategoryBackup.toEntity(): BillCategoryEntity = BillCategoryEntity(id, name, iconKey, colorHex, isCustom)

fun IncomeCategoryBackup.toEntity(): IncomeCategoryEntity = IncomeCategoryEntity(id, name, iconKey, colorHex, isCustom)
fun IncomeSourceBackup.toEntity(): IncomeSourceEntity = IncomeSourceEntity(
    id = id, categoryId = categoryId, name = name, defaultAmount = defaultAmount,
    recurrenceType = recurrenceType, recurrenceInterval = recurrenceInterval,
    startDate = kotlinx.datetime.LocalDate.parse(startDate),
    endDate = endDate?.let { kotlinx.datetime.LocalDate.parse(it) },
    isActive = isActive, isArchived = false, isDeleted = isDeleted
)
fun IncomeEntryBackup.toEntity(): IncomeEntryEntity = IncomeEntryEntity(
    id = id, incomeSourceId = incomeSourceId, categoryId = categoryId, amount = amount,
    currencyCode = currencyCode, entryDate = kotlinx.datetime.LocalDate.parse(entryDate),
    note = note, createdAt = kotlinx.datetime.LocalDateTime.parse(createdAt)
)

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

fun BudgetBackup.toEntity(): BudgetEntity = BudgetEntity(
    id = id, name = name, type = type, totalAmount = totalAmount, currencyCode = currencyCode,
    startDate = kotlinx.datetime.LocalDate.parse(startDate), endDate = kotlinx.datetime.LocalDate.parse(endDate),
    isActive = isActive, isArchived = isArchived, isDeleted = false,
    createdAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    updatedAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
)
fun BudgetLimitBackup.toEntity(): BudgetCategoryLimitEntity = BudgetCategoryLimitEntity(id, budgetId, categoryId, limitAmount)

fun SavingsGoalBackup.toEntity(): SavingsGoalEntity = SavingsGoalEntity(
    id = id, name = name, targetAmount = targetAmount, currencyCode = currencyCode, currentAmount = currentAmount,
    targetDate = targetDate?.let { kotlinx.datetime.LocalDate.parse(it) }, iconKey = iconKey, colorHex = colorHex,
    notes = notes, priority = priority, isCompleted = isCompleted, isArchived = isArchived, isDeleted = false,
    createdAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
)
fun SavingsContributionBackup.toEntity(): SavingsContributionEntity = SavingsContributionEntity(
    id = id, savingsGoalId = savingsGoalId, amount = amount, contributionDate = kotlinx.datetime.LocalDate.parse(contributionDate),
    note = note, createdAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
)
