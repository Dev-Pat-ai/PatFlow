package com.patflow.app.feature.bills

import app.cash.turbine.test
import com.patflow.app.domain.usecase.bill.DeleteBillUseCase
import com.patflow.app.domain.usecase.bill.GetBillsUseCase
import com.patflow.app.domain.usecase.bill.MarkBillAsPaidUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BillListViewModelTest {

    private val getBillsUseCase: GetBillsUseCase = mockk()
    private val deleteBillUseCase: DeleteBillUseCase = mockk()
    private val markBillAsPaidUseCase: MarkBillAsPaidUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        every { getBillsUseCase() } returns flowOf(emptyList())
        
        val viewModel = BillListViewModel(getBillsUseCase, deleteBillUseCase, markBillAsPaidUseCase)
        
        viewModel.uiState.test {
            val item = awaitItem()
            assertTrue(item is BillListUiState.Loading)
            // Need to trigger collect to get Success
            val next = awaitItem()
            assertTrue(next is BillListUiState.Success)
        }
    }
}
