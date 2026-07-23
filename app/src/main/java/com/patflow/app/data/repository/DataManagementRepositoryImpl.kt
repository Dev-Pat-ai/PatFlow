package com.patflow.app.data.repository

import android.os.Build
import androidx.room.withTransaction
import com.patflow.app.data.local.dao.*
import com.patflow.app.data.local.database.PatFlowDatabase
import com.patflow.app.data.mapper.toBackup
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.*
import com.patflow.app.domain.repository.DataManagementRepository
import com.patflow.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Implementation of [DataManagementRepository] using Room and DataStore (Architecture §9 / Phase 7B).
 * Orchestrates full application state backup and transactional restoration.
 */
class DataManagementRepositoryImpl @Inject constructor(
    private val database: PatFlowDatabase,
    private val billDao: BillDao,
    private val billCycleDao: BillCycleDao,
    private val paymentDao: PaymentDao,
    private val categoryDao: CategoryDao,
    private val settingsRepository: SettingsRepository
) : DataManagementRepository {

    override suspend fun createBackup(): BackupModel {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val prefs = settingsRepository.getUserPreferences().first()
        
        val appData = AppDataBackup(
            categories = categoryDao.getAllEntities().map { it.toBackup() },
            bills = billDao.getAllEntities().map { it.toBackup() },
            billCycles = billCycleDao.getAllEntities().map { it.toBackup() },
            payments = paymentDao.getAllEntities().map { it.toBackup() },
            profile = UserProfileBackup(
                displayName = prefs.profile.displayName,
                monthlyBudget = prefs.profile.monthlyBudget,
                preferredCurrency = prefs.profile.preferredCurrency,
                preferredTheme = prefs.profile.preferredTheme.name
            ),
            preferences = UserPreferencesBackup(
                useDynamicColor = prefs.useDynamicColor,
                dateFormat = prefs.dateFormat,
                firstDayOfWeek = prefs.firstDayOfWeek,
                hapticFeedbackEnabled = prefs.hapticFeedbackEnabled
            )
        )

        return BackupModel(
            schemaVersion = 1,
            appVersion = "1.0.0-RC1",
            createdAt = now.toString(),
            device = Build.MODEL,
            data = appData
        )
    }

    override suspend fun restoreBackup(backup: BackupModel) {
        database.withTransaction {
            // 1. Clear existing data
            paymentDao.deleteAll()
            billCycleDao.deleteAll()
            billDao.deleteAll()
            categoryDao.deleteAll()
            
            // 2. Insert backup data
            categoryDao.insertAll(backup.data.categories.map { it.toEntity() })
            billDao.insertAll(backup.data.bills.map { it.toEntity() })
            billCycleDao.insertAll(backup.data.billCycles.map { it.toEntity() })
            paymentDao.insertAll(backup.data.payments.map { it.toEntity() })
            
            // 3. Restore preferences
            val themeMode = try {
                ThemeMode.valueOf(backup.data.profile.preferredTheme)
            } catch (e: Exception) {
                ThemeMode.SYSTEM
            }

            settingsRepository.restoreAll(
                UserPreferences(
                    profile = UserProfile(
                        displayName = backup.data.profile.displayName,
                        monthlyBudget = backup.data.profile.monthlyBudget,
                        preferredCurrency = backup.data.profile.preferredCurrency,
                        preferredTheme = themeMode
                    ),
                    useDynamicColor = backup.data.preferences.useDynamicColor,
                    dateFormat = backup.data.preferences.dateFormat,
                    firstDayOfWeek = backup.data.preferences.firstDayOfWeek,
                    hapticFeedbackEnabled = backup.data.preferences.hapticFeedbackEnabled
                )
            )
        }
    }

    override suspend fun generateCsvExport(): Map<String, String> {
        val bills = billDao.getAllEntities()
        val payments = paymentDao.getAllWithBillDetails().first()
        
        val billsCsv = StringBuilder("ID,Name,Category ID,Default Amount,Currency,Recurrence,Start Date\n")
        bills.forEach {
            billsCsv.append("${it.id},${it.name},${it.categoryId},${it.defaultAmount},${it.currencyCode},${it.recurrenceType},${it.startDate}\n")
        }
        
        val paymentsCsv = StringBuilder("ID,Bill Name,Amount,Date,Method,Note\n")
        payments.forEach {
            paymentsCsv.append("${it.payment.id},${it.billName},${it.payment.amount},${it.payment.paymentDate},${it.payment.method},${it.payment.note ?: ""}\n")
        }
        
        return mapOf(
            "bills_export.csv" to billsCsv.toString(),
            "payments_export.csv" to paymentsCsv.toString()
        )
    }
}
