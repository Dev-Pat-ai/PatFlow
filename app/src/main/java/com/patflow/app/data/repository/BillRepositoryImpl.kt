package com.patflow.app.data.repository

import com.patflow.app.data.local.dao.BillCycleDao
import com.patflow.app.data.local.dao.BillDao
import com.patflow.app.data.local.dao.PaymentDao
import com.patflow.app.data.local.entity.BillCycleEntity
import com.patflow.app.data.local.entity.PaymentEntity
import com.patflow.app.data.mapper.toDomain
import com.patflow.app.data.mapper.toEntity
import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.model.BillCycle
import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.model.PaymentMethod
import com.patflow.app.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class BillRepositoryImpl @Inject constructor(
    private val billDao: BillDao,
    private val billCycleDao: BillCycleDao,
    private val paymentDao: PaymentDao
) : BillRepository {

    override fun getBillsWithCycles(): Flow<List<BillWithCycle>> {
        return billDao.getAllWithCategory().map { map ->
            map.map { (billEntity, categoryEntity) ->
                val bill = billEntity.toDomain(categoryEntity.toDomain())
                // In a real implementation, we might want to get the *current* cycle
                // For MVP, we'll just get the latest one for each bill
                // This is a bit simplified for now
                BillWithCycle(bill, null) 
            }
        }
    }

    override fun getBillById(id: Long): Flow<Bill?> = flow {
        val map = billDao.getBillWithCategoryById(id)
        if (map == null) {
            emit(null)
        } else {
            val (billEntity, categoryEntity) = map.entries.first()
            emit(billEntity.toDomain(categoryEntity.toDomain()))
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
        billCycleDao.insert(firstCycle)
        
        return billId
    }

    override suspend fun updateBill(bill: Bill) {
        billDao.update(bill.toEntity())
    }

    override suspend fun deleteBill(id: Long) {
        val bill = billDao.getBillWithCategoryById(id)?.keys?.first()
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

    override suspend fun markCycleAsPaid(cycleId: Long, amount: Double, method: PaymentMethod) {
        val cycle = billCycleDao.getById(cycleId) ?: return
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        
        // 1. Log Payment
        val payment = PaymentEntity(
            billCycleId = cycleId,
            amount = amount,
            paymentDate = now.date,
            method = method.name,
            createdAt = now
        )
        paymentDao.insert(payment)
        
        // 2. Update Cycle
        val newPaid = cycle.amountPaid + amount
        val newStatus = if (newPaid >= cycle.amountDue) BillStatus.PAID else BillStatus.PARTIALLY_PAID
        
        billCycleDao.update(cycle.copy(
            amountPaid = newPaid,
            status = newStatus.name,
            updatedAt = now
        ))
    }
}
