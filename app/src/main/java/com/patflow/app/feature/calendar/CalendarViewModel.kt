package com.patflow.app.feature.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patflow.app.domain.model.*
import com.patflow.app.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val billRepository: BillRepository,
    private val paymentRepository: PaymentRepository,
    private val incomeRepository: IncomeRepository,
    private val savingsRepository: SavingsGoalRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    val uiState: StateFlow<CalendarUiState> = combine(
        billRepository.getBillsWithCycles(),
        paymentRepository.getPayments(),
        incomeRepository.getEntries(),
        savingsRepository.getGoals(),
        _selectedDate
    ) { args: Array<Any?> ->
        @Suppress("UNCHECKED_CAST")
        val billDetails = args[0] as List<BillWithCycle>
        @Suppress("UNCHECKED_CAST")
        val payments = args[1] as List<PaymentHistory>
        @Suppress("UNCHECKED_CAST")
        val income = args[2] as List<IncomeWithDetails>
        // val goals = args[3] as List<SavingsGoal> // Not used for now
        val date = args[4] as LocalDate

        val allEvents = mutableListOf<CalendarEvent>()
        
        billDetails.forEach { detail ->
            detail.currentCycle?.let { cycle ->
                allEvents.add(CalendarEvent.BillDue(cycle.dueDate, detail.bill, cycle))
            }
        }

        payments.forEach { p ->
            allEvents.add(CalendarEvent.PaymentMade(p.payment.paymentDate, p.billName, p.payment))
        }

        income.forEach { inc ->
            allEvents.add(CalendarEvent.IncomeReceived(inc.entry.entryDate, inc.sourceName ?: "Income", inc.entry))
        }

        val dayEvents = allEvents.filter { it.date == date }

        CalendarUiState.Success(
            selectedDate = date,
            allEvents = allEvents,
            dayEvents = dayEvents
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CalendarUiState.Loading
    )

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
    }
}

sealed interface CalendarEvent {
    val date: LocalDate
    data class BillDue(override val date: LocalDate, val bill: Bill, val cycle: BillCycle) : CalendarEvent
    data class PaymentMade(override val date: LocalDate, val billName: String, val payment: Payment) : CalendarEvent
    data class IncomeReceived(override val date: LocalDate, val sourceName: String, val entry: IncomeEntry) : CalendarEvent
}

sealed interface CalendarUiState {
    data object Loading : CalendarUiState
    data class Success(
        val selectedDate: LocalDate,
        val allEvents: List<CalendarEvent>,
        val dayEvents: List<CalendarEvent>
    ) : CalendarUiState
}
