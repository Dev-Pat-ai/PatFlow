package com.patflow.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.patflow.app.data.local.dao.BillCycleDao
import com.patflow.app.data.local.dao.BillDao
import com.patflow.app.data.local.dao.BudgetDao
import com.patflow.app.data.local.dao.CategoryDao
import com.patflow.app.data.local.dao.IncomeDao
import com.patflow.app.data.local.dao.PaymentDao
import com.patflow.app.data.local.dao.ReminderDao
import com.patflow.app.data.local.dao.SavingsGoalDao
import com.patflow.app.data.local.dao.SearchDao
import com.patflow.app.data.local.database.DatabaseSeeder
import com.patflow.app.data.local.database.PatFlowDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

/** Provides the single Room database instance and its DAOs (Architecture §9 — di/). */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PatFlowDatabase =
        Room.databaseBuilder(
            context,
            PatFlowDatabase::class.java,
            PatFlowDatabase.DATABASE_NAME,
        )
            .fallbackToDestructiveMigration() // Dev-only: Architecture §8.1 note
            .build()

    @Provides
    fun provideCategoryDao(db: PatFlowDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideBillDao(db: PatFlowDatabase): BillDao = db.billDao()

    @Provides
    fun provideBillCycleDao(db: PatFlowDatabase): BillCycleDao = db.billCycleDao()

    @Provides
    fun providePaymentDao(db: PatFlowDatabase): PaymentDao = db.paymentDao()

    @Provides
    fun provideBudgetDao(db: PatFlowDatabase): BudgetDao = db.budgetDao()

    @Provides
    fun provideReminderDao(db: PatFlowDatabase): ReminderDao = db.reminderDao()

    @Provides
    fun provideIncomeDao(db: PatFlowDatabase): IncomeDao = db.incomeDao()

    @Provides
    fun provideSavingsGoalDao(db: PatFlowDatabase): SavingsGoalDao = db.savingsGoalDao()

    @Provides
    fun provideSearchDao(db: PatFlowDatabase): SearchDao = db.searchDao()
}
