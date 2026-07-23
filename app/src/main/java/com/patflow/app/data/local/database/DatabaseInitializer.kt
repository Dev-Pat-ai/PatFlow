package com.patflow.app.data.local.database

import com.patflow.app.data.local.dao.CategoryDao
import com.patflow.app.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles database seeding and first-run initialization (Architecture §8.3).
 * Decoupled from DatabaseModule to avoid circular dependencies.
 */
@Singleton
class DatabaseInitializer @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            val existing = categoryDao.getAll().first()
            if (existing.isEmpty()) {
                DatabaseSeeder.getPredefinedCategories().forEach {
                    categoryDao.insert(it)
                }
            }
        }
    }
}
