package com.patflow.app.domain.repository

import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.model.BillCycle
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

interface BillRepository {
    fun getBillsWithCycles(): Flow<List<BillWithCycle>>
    fun getBillById(id: Long): Flow<Bill?>
    suspend fun insertBill(bill: Bill): Long
    suspend fun updateBill(bill: Bill)
    suspend fun deleteBill(id: Long)
    
    fun getCyclesForBill(billId: Long): Flow<List<BillCycle>>
    suspend fun getCycleById(id: Long): BillCycle?
    suspend fun markCycleAsPaid(cycleId: Long, amount: Double, method: PaymentMethod)
}
