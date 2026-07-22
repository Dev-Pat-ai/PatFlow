package com.patflow.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patflow.app.data.local.dao.BillCycleDao
import com.patflow.app.data.local.dao.BillDao
import com.patflow.app.data.local.dao.BudgetDao
import com.patflow.app.data.local.dao.CategoryDao
import com.patflow.app.data.local.dao.IncomeDao
import com.patflow.app.data.local.dao.PaymentDao
import com.patflow.app.data.local.dao.ReminderDao
import com.patflow.app.data.local.dao.SavingsGoalDao
import com.patflow.app.data.local.dao.SearchDao
import com.patflow.app.data.local.entity.BillCategoryEntity
import com.patflow.app.data.local.entity.BillCycleEntity
import com.patflow.app.data.local.entity.BillEntity
import com.patflow.app.data.local.entity.BillSearchFtsEntity
import com.patflow.app.data.local.entity.BudgetCategoryLimitEntity
import com.patflow.app.data.local.entity.BudgetEntity
import com.patflow.app.data.local.entity.IncomeCategoryEntity
import com.patflow.app.data.local.entity.IncomeEntryEntity
import com.patflow.app.data.local.entity.IncomeSourceEntity
import com.patflow.app.data.local.entity.PaymentEntity
import com.patflow.app.data.local.entity.RecentSearchEntity
import com.patflow.app.data.local.entity.ReminderEntity
import com.patflow.app.data.local.entity.SavingsContributionEntity
import com.patflow.app.data.local.entity.SavingsGoalEntity

/**
 * PatFlow's single Room database — offline-first, single source of truth
 * (Architecture, front matter). All 14 tables from Architecture §8 are
 * registered here. Room as single source of truth means there is no other
 * persistence path for this data in v1.
 *
 * Version starts at 1. Migrations object below is intentionally empty for
 * now — every schema change from this point forward must add a real
 * Migration rather than relying on destructive fallback in production code
 * (fallbackToDestructiveMigration is a dev-only convenience, wired in
 * DatabaseModule, not used here).
 */
@Database(
    entities = [
        BillCategoryEntity::class,
        BillEntity::class,
        BillCycleEntity::class,
        PaymentEntity::class,
        BudgetEntity::class,
        BudgetCategoryLimitEntity::class,
        ReminderEntity::class,
        IncomeCategoryEntity::class,
        IncomeSourceEntity::class,
        IncomeEntryEntity::class,
        SavingsGoalEntity::class,
        SavingsContributionEntity::class,
        RecentSearchEntity::class,
        BillSearchFtsEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class PatFlowDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun billDao(): BillDao
    abstract fun billCycleDao(): BillCycleDao
    abstract fun paymentDao(): PaymentDao
    abstract fun budgetDao(): BudgetDao
    abstract fun reminderDao(): ReminderDao
    abstract fun incomeDao(): IncomeDao
    abstract fun savingsGoalDao(): SavingsGoalDao
    abstract fun searchDao(): SearchDao

    companion object {
        const val DATABASE_NAME = "patflow.db"
    }
}

/**
 * Schema migrations. Empty at version 1 — the first real entry here will be
 * MIGRATION_1_2 once a schema change ships (e.g. activating the Installment
 * Bills columns per Architecture §8.6, or v2 sync scaffold activation).
 */
object PatFlowMigrations {
    val ALL = arrayOf<androidx.room.migration.Migration>()
}
