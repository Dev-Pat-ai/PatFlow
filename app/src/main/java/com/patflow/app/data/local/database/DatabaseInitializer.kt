package com.patflow.app.data.local.database

import com.patflow.app.data.local.dao.CategoryDao
import com.patflow.app.data.local.dao.IncomeDao
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
    private val categoryDao: CategoryDao,
    private val incomeDao: IncomeDao
) {
    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            // Bill Categories
            val existingBills = categoryDao.getAll().first()
            if (existingBills.isEmpty()) {
                DatabaseSeeder.getPredefinedCategories().forEach {
                    categoryDao.insert(it)
                }
            }

            // Income Categories
            val existingIncome = incomeDao.getAllCategories().first()
            if (existingIncome.isEmpty()) {
                DatabaseSeeder.getPredefinedIncomeCategories().forEach {
                    incomeDao.insertCategory(it)
                }
            }
        }
    }
}
