package com.patflow.app.data.repository

import com.patflow.app.data.local.dao.IncomeDao
import com.patflow.app.data.mapper.toDomain
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.IncomeCategory
import com.patflow.app.domain.model.IncomeEntry
import com.patflow.app.domain.model.IncomeSource
import com.patflow.app.domain.model.IncomeWithDetails
import com.patflow.app.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val incomeDao: IncomeDao
) : IncomeRepository {

    override fun getCategories(): Flow<List<IncomeCategory>> =
        incomeDao.getAllCategories().map { entities -> entities.map { it.toDomain() } }

    override suspend fun insertCategory(category: IncomeCategory): Long =
        incomeDao.insertCategory(category.toEntity())

    override fun getSources(): Flow<List<IncomeSource>> =
        combine(incomeDao.getAllSources(), getCategories()) { entities, categories ->
            entities.map { entity ->
                val category = categories.find { it.id == entity.categoryId }!!
                entity.toDomain(category)
            }
        }

    override suspend fun getSourceById(id: Long): IncomeSource? {
        val entity = incomeDao.getSourceById(id) ?: return null
        val categories = getCategories().first()
        val category = categories.find { it.id == entity.categoryId }!!
        return entity.toDomain(category)
    }

    override suspend fun insertSource(source: IncomeSource): Long =
        incomeDao.insertSource(source.toEntity())

    override suspend fun updateSource(source: IncomeSource) =
        incomeDao.updateSource(source.toEntity())

    override suspend fun deleteSource(id: Long) {
        val source = incomeDao.getSourceById(id)
        source?.let {
            incomeDao.updateSource(it.copy(isDeleted = true))
        }
    }

    override fun getEntries(): Flow<List<IncomeWithDetails>> =
        combine(incomeDao.getAllEntries(), getCategories(), getSources()) { entities, categories, sources ->
            entities.map { entity ->
                val category = categories.find { it.id == entity.categoryId }!!
                val sourceName = sources.find { it.id == entity.incomeSourceId }?.name
                IncomeWithDetails(entity.toDomain(category), sourceName)
            }
        }

    override fun getEntriesByDateRange(start: String, end: String): Flow<List<IncomeWithDetails>> =
        combine(incomeDao.getEntriesByDateRange(start, end), getCategories(), getSources()) { entities, categories, sources ->
            entities.map { entity ->
                val category = categories.find { it.id == entity.categoryId }!!
                val sourceName = sources.find { it.id == entity.incomeSourceId }?.name
                IncomeWithDetails(entity.toDomain(category), sourceName)
            }
        }

    override suspend fun getEntryById(id: Long): IncomeWithDetails? {
        val entity = incomeDao.getEntryById(id) ?: return null
        val categories = getCategories().first()
        val category = categories.find { it.id == entity.categoryId }!!
        val sources = getSources().first()
        val sourceName = sources.find { it.id == entity.incomeSourceId }?.name
        return IncomeWithDetails(entity.toDomain(category), sourceName)
    }

    override suspend fun insertEntry(entry: IncomeEntry): Long =
        incomeDao.insertEntry(entry.toEntity())

    override suspend fun updateEntry(entry: IncomeEntry) =
        incomeDao.updateEntry(entry.toEntity())

    override suspend fun deleteEntry(id: Long) {
        val entity = incomeDao.getEntryById(id)
        entity?.let { incomeDao.deleteEntry(it) }
    }
}
