package com.patflow.app.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Reserved — repository implementations (BillRepositoryImpl, PaymentRepositoryImpl,
 * etc.) bind to their domain interfaces here once they're built alongside the
 * Bills feature. Left empty and present (not omitted) since DatabaseModule
 * already depends on it existing as a sibling module in di/.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule
