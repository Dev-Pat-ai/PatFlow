package com.patflow.app.di

import com.patflow.app.data.repository.BillRepositoryImpl
import com.patflow.app.data.repository.CategoryRepositoryImpl
import com.patflow.app.data.repository.DataManagementRepositoryImpl
import com.patflow.app.data.repository.IncomeRepositoryImpl
import com.patflow.app.data.repository.NotificationRepositoryImpl
import com.patflow.app.data.repository.PaymentRepositoryImpl
import com.patflow.app.data.repository.ReminderRepositoryImpl
import com.patflow.app.data.repository.SettingsRepositoryImpl
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.CategoryRepository
import com.patflow.app.domain.repository.DataManagementRepository
import com.patflow.app.domain.repository.IncomeRepository
import com.patflow.app.domain.repository.NotificationRepository
import com.patflow.app.domain.repository.PaymentRepository
import com.patflow.app.domain.repository.ReminderRepository
import com.patflow.app.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository implementations bind to their domain interfaces here (Architecture §9).
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBillRepository(
        impl: BillRepositoryImpl
    ): BillRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(
        impl: PaymentRepositoryImpl
    ): PaymentRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindDataManagementRepository(
        impl: DataManagementRepositoryImpl
    ): DataManagementRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindReminderRepository(
        impl: ReminderRepositoryImpl
    ): ReminderRepository

    @Binds
    @Singleton
    abstract fun bindIncomeRepository(
        impl: IncomeRepositoryImpl
    ): IncomeRepository
}
