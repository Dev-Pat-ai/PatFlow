package com.patflow.app.data.repository

import com.patflow.app.data.local.dao.BillCycleDao
import com.patflow.app.data.local.dao.BillDao
import com.patflow.app.data.local.entity.BillCycleEntity
import com.patflow.app.data.mapper.toDomain
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.model.BillCycle
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.model.Reminder
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.ReminderRepository
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Implementation of [BillRepository] using Room DAOs (Architecture §9).
 * Handles the single source of truth for bill templates and cycles.
 */
class BillRepositoryImpl @Inject constructor(
    private val billDao: BillDao,
    private val billCycleDao: BillCycleDao,
    private val reminderRepository: ReminderRepository,
    private val getUserSettingsUseCase: GetUserSettingsUseCase
) : BillRepository {

    override fun getBillsWithCycles(): Flow<List<BillWithCycle>> {
        return billDao.getBillsWithDetails().map { list ->
            list.map { entity ->
                BillWithCycle(
                    bill = entity.bill.toDomain(entity.category.toDomain()),
                    currentCycle = entity.cycles.maxByOrNull { it.dueDate }?.toDomain()
                )
            }
        }
    }

    override fun getBillById(id: Long): Flow<Bill?> = flow {
        val map = billDao.getBillWithCategoryById(id)
        if (map.isNullOrEmpty()) {
            emit(null)
        } else {
            val entry = map.entries.first()
            emit(entry.key.toDomain(entry.value.toDomain()))
        }
    }

    override suspend fun insertBill(bill: Bill): Long {
        val billId = billDao.insert(bill.toEntity())
        
        // Generate first cycle
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val firstCycle = BillCycleEntity(
            billId = billId,
            periodStart = bill.recurrence.startDate,
            dueDate = bill.recurrence.startDate, // Simplified for now
            amountDue = bill.defaultAmount,
            amountPaid = 0.0,
            status = BillStatus.UNPAID.name,
            createdAt = now,
            updatedAt = now
        )
        val cycleId = billCycleDao.insert(firstCycle)
        
        scheduleRemindersForCycle(cycleId, bill.recurrence.startDate)
        
        return billId
    }

    private suspend fun scheduleRemindersForCycle(cycleId: Long, dueDate: kotlinx.datetime.LocalDate) {
        val settings = getUserSettingsUseCase().first()
        if (!settings.notificationsMasterEnabled) return

        for (offset in settings.reminderOffsets) {
            val remindAtDate = dueDate.minus(DatePeriod(days = offset))
            // Remind at 9:00 AM on the calculated day
            val remindAt = kotlinx.datetime.LocalDateTime(
                remindAtDate.year, remindAtDate.month, remindAtDate.dayOfMonth, 9, 0
            )
            
            reminderRepository.insertReminder(
                Reminder(
                    billCycleId = cycleId,
                    remindAt = remindAt,
                    offsetDays = offset
                )
            )
        }
    }

    override suspend fun updateBill(bill: Bill) {
        billDao.update(bill.toEntity())
    }

    override suspend fun deleteBill(id: Long) {
        val map = billDao.getBillWithCategoryById(id)
        val bill = map?.keys?.firstOrNull()
        bill?.let {
            billDao.update(it.copy(isDeleted = true))
        }
    }

    override fun getCyclesForBill(billId: Long): Flow<List<BillCycle>> {
        return billCycleDao.getByBill(billId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCyclesByDateRange(start: String, end: String): Flow<List<BillCycle>> {
        return billCycleDao.getByDateRange(start, end).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getUpcomingCycles(limit: Int): Flow<List<BillCycle>> {
        return billCycleDao.getUpcoming(limit).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getCycleById(id: Long): BillCycle? {
        return billCycleDao.getById(id)?.toDomain()
    }

    override suspend fun updateCyclePaidAmount(cycleId: Long, deltaAmount: Double) {
        val cycle = billCycleDao.getById(cycleId) ?: return
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        
        val newPaid = (cycle.amountPaid + deltaAmount).coerceAtLeast(0.0)
        val newStatus = when {
            newPaid >= cycle.amountDue -> BillStatus.PAID
            // In production, we'd also check if it's OVERDUE based on current date
            else -> BillStatus.UNPAID
        }
        
        billCycleDao.update(cycle.copy(
            amountPaid = newPaid,
            status = newStatus.name,
            updatedAt = now
        ))

        if (newStatus == BillStatus.PAID) {
            reminderRepository.deleteRemindersForCycle(cycleId)
        }
    }
}
