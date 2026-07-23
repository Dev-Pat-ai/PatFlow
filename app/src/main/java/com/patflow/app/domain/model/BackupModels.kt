package com.patflow.app.domain.model

import kotlinx.serialization.Serializable

/**
 * Root model for application data backup (Architecture §1.10 / Phase 7B).
 * Supports structured versioning for future-proof migrations.
 */
@Serializable
data class BackupModel(
    val schemaVersion: Int,
    val appVersion: String,
    val createdAt: String,
    val device: String,
    val data: AppDataBackup
)

/**
 * Container for all application data tables.
 */
@Serializable
data class AppDataBackup(
    val categories: List<CategoryBackup>,
    val bills: List<BillBackup>,
    val billCycles: List<BillCycleBackup>,
    val payments: List<PaymentBackup>,
    val profile: UserProfileBackup,
    val preferences: UserPreferencesBackup
)

@Serializable
data class CategoryBackup(
    val id: Long,
    val name: String,
    val iconKey: String,
    val colorHex: String,
    val isCustom: Boolean
)

@Serializable
data class BillBackup(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val defaultAmount: Double,
    val currencyCode: String,
    val merchant: String?,
    val recurrenceType: String,
    val recurrenceInterval: Int,
    val dueDay: Int?,
    val startDate: String,
    val endDate: String?,
    val isActive: Boolean,
    val isFavorite: Boolean,
    val notes: String?
)

@Serializable
data class BillCycleBackup(
    val id: Long,
    val billId: Long,
    val periodStart: String,
    val dueDate: String,
    val amountDue: Double,
    val amountPaid: Double,
    val status: String
)

@Serializable
data class PaymentBackup(
    val id: Long,
    val billCycleId: Long,
    val amount: Double,
    val paymentDate: String,
    val method: String,
    val note: String?
)

@Serializable
data class UserProfileBackup(
    val displayName: String,
    val monthlyBudget: Double?,
    val preferredCurrency: String,
    val preferredTheme: String
)

@Serializable
data class UserPreferencesBackup(
    val useDynamicColor: Boolean,
    val dateFormat: String,
    val firstDayOfWeek: Int,
    val hapticFeedbackEnabled: Boolean
)
