package com.patflow.app.domain.repository

import com.patflow.app.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Interface for category-related data operations (Architecture §1.14).
 * Manages predefined and custom categories.
 */
interface CategoryRepository {
    fun getCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Category?
    suspend fun insertCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}
