package com.patflow.app.di

import com.patflow.app.data.repository.BillRepositoryImpl
import com.patflow.app.data.repository.CategoryRepositoryImpl
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository implementations (BillRepositoryImpl, CategoryRepositoryImpl) 
 * bind to their domain interfaces here.
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
}
