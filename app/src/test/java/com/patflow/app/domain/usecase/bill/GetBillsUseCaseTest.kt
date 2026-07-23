package com.patflow.app.domain.usecase.bill

import app.cash.turbine.test
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.repository.BillRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetBillsUseCaseTest {

    private val repository: BillRepository = mockk()
    private val useCase = GetBillsUseCase(repository)

    @Test
    fun `invoke should return bills from repository`() = runTest {
        val bills = listOf<BillWithCycle>(mockk(), mockk())
        every { repository.getBillsWithCycles() } returns flowOf(bills)
        
        useCase().test {
            val result = awaitItem()
            assertEquals(bills.size, result.size)
            awaitComplete()
        }
    }
}
