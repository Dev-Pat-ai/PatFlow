package com.patflow.app.data.mapper

import com.patflow.app.data.local.entity.SavingsContributionEntity
import com.patflow.app.data.local.entity.SavingsGoalEntity
import com.patflow.app.domain.model.SavingsContribution
import com.patflow.app.domain.model.SavingsGoal

fun SavingsGoalEntity.toDomain(): SavingsGoal = SavingsGoal(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currencyCode = currencyCode,
    currentAmount = currentAmount,
    targetDate = targetDate,
    iconKey = iconKey,
    colorHex = colorHex,
    notes = notes,
    priority = priority,
    isCompleted = isCompleted,
    isArchived = isArchived,
    isDeleted = isDeleted,
    createdAt = createdAt
)

fun SavingsGoal.toEntity(): SavingsGoalEntity = SavingsGoalEntity(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currencyCode = currencyCode,
    currentAmount = currentAmount,
    targetDate = targetDate,
    iconKey = iconKey,
    colorHex = colorHex,
    notes = notes,
    priority = priority,
    isCompleted = isCompleted,
    isArchived = isArchived,
    isDeleted = isDeleted,
    createdAt = createdAt
)

fun SavingsContributionEntity.toDomain(): SavingsContribution = SavingsContribution(
    id = id,
    savingsGoalId = savingsGoalId,
    amount = amount,
    contributionDate = contributionDate,
    note = note,
    createdAt = createdAt
)

fun SavingsContribution.toEntity(): SavingsContributionEntity = SavingsContributionEntity(
    id = id,
    savingsGoalId = savingsGoalId,
    amount = amount,
    contributionDate = contributionDate,
    note = note,
    createdAt = createdAt
)
