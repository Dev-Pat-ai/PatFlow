package com.patflow.app.domain.usecase.bill

import com.patflow.app.domain.model.Bill
import com.patflow.app.domain.model.BillCycle
import com.patflow.app.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class BillDetail(
    val bill: Bill,
    val cycles: List<BillCycle>
)

class GetBillDetailUseCase @Inject constructor(
    private val repository: BillRepository
) {
    operator fun invoke(billId: Long): Flow<BillDetail?> {
        return combine(
            repository.getBillById(billId),
            repository.getCyclesForBill(billId)
        ) { bill, cycles ->
            bill?.let { BillDetail(it, cycles) }
        }
    }
}
