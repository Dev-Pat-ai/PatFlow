package com.patflow.app.data.mapper

import com.patflow.app.data.local.entity.BudgetCategoryLimitEntity
import com.patflow.app.data.local.entity.BudgetEntity
import com.patflow.app.domain.model.Budget
import com.patflow.app.domain.model.BudgetLimit
import com.patflow.app.domain.model.BudgetType
import com.patflow.app.domain.model.Category
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun BudgetEntity.toDomain(): Budget = Budget(
    id = id,
    name = name,
    type = BudgetType.valueOf(type),
    totalAmount = totalAmount,
    currencyCode = currencyCode,
    startDate = startDate,
    endDate = endDate,
    isActive = isActive,
    isArchived = isArchived,
    isDeleted = isDeleted,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Budget.toEntity(): BudgetEntity = BudgetEntity(
    id = id,
    name = name,
    type = type.name,
    totalAmount = totalAmount,
    currencyCode = currencyCode,
    startDate = startDate,
    endDate = endDate,
    isActive = isActive,
    isArchived = isArchived,
    isDeleted = isDeleted,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun BudgetCategoryLimitEntity.toDomain(category: Category): BudgetLimit = BudgetLimit(
    id = id,
    budgetId = budgetId,
    category = category,
    limitAmount = limitAmount
)

fun BudgetLimit.toEntity(): BudgetCategoryLimitEntity = BudgetCategoryLimitEntity(
    id = id,
    budgetId = budgetId,
    categoryId = category.id,
    limitAmount = limitAmount
)
