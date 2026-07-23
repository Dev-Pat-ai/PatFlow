package com.patflow.app.domain.usecase.dashboard

import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.model.DashboardData
import com.patflow.app.domain.repository.BillRepository
import com.patflow.app.domain.repository.IncomeRepository
import com.patflow.app.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import java.util.Locale
import javax.inject.Inject

/**
 * Use case for aggregating all data required for the Dashboard screen (Architecture §1.3).
 * Consolidates metrics, upcoming bills, recent payments, and trend data.
 */
class GetDashboardDataUseCase @Inject constructor(
    private val billRepository: BillRepository,
    private val paymentRepository: PaymentRepository,
    private val incomeRepository: IncomeRepository
) {
    operator fun invoke(): Flow<DashboardData> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val startOfMonth = LocalDate(now.year, now.month, 1)
        val endOfMonth = (startOfMonth + DatePeriod(months = 1)) - DatePeriod(days = 1)
        val startOfYear = LocalDate(now.year, 1, 1)
        val sixMonthsAgo = startOfMonth - DatePeriod(months = 5)

        return combine(
            billRepository.getBillsWithCycles(),
            billRepository.getCyclesByDateRange(startOfMonth.toString(), endOfMonth.toString()),
            paymentRepository.getPayments(),
            incomeRepository.getEntries()
        ) { allBills, monthCycles, allPayments, allIncome ->
            
            val totalDue = monthCycles.sumOf { it.amountDue }
            val totalPaid = monthCycles.sumOf { it.amountPaid }
            val remaining = (totalDue - totalPaid).coerceAtLeast(0.0)
            
            val monthIncome = allIncome.filter { it.entry.entryDate in startOfMonth..endOfMonth }
            val totalIncomeMonth = monthIncome.sumOf { it.entry.amount }
            val totalIncomeYear = allIncome.filter { it.entry.entryDate >= startOfYear }.sumOf { it.entry.amount }
            
            val totalAllIncome = allIncome.sumOf { it.entry.amount }
            val totalAllPaid = allPayments.sumOf { it.payment.amount }
            val netBalance = totalAllIncome - totalAllPaid
            
            val netCashFlow = totalIncomeMonth - totalPaid
            
            val dueToday = monthCycles.count { (it.dueDate == now) && (it.status != BillStatus.PAID) }
            val overdue = monthCycles.count { it.status == BillStatus.OVERDUE }
            
            val upcoming = monthCycles.asSequence()
                .filter { it.status != BillStatus.PAID }
                .sortedBy { it.dueDate }
                .take(5)
                .mapNotNull { cycle ->
                    val bill = allBills.find { it.bill.id == cycle.billId }?.bill
                    bill?.let { BillWithCycle(it, cycle) }
                }
                .toList()

            val recentPayments = allPayments.take(5)

            // Spending Trend (last 6 months)
            val trend = allPayments
                .filter { it.payment.paymentDate >= sixMonthsAgo }
                .groupBy { 
                    val date = it.payment.paymentDate
                    "${date.month.name.take(3)} ${date.year}"
                }
                .mapValues { it.value.sumOf { p -> p.payment.amount } }
                .toSortedMap()

            // Spending by Category (this month)
            val byCategory = monthCycles.groupBy { cycle -> 
                allBills.find { it.bill.id == cycle.billId }?.bill?.category
            }.mapNotNull { (category, cycles) ->
                category?.let { it to cycles.sumOf { c -> c.amountPaid } }
            }.toMap()

            // Income by Category (this month)
            val incomeByCategory = monthIncome.groupBy { it.entry.category }
                .mapValues { it.value.sumOf { e -> e.entry.amount } }

            // Insights
            val insights = mutableListOf<String>()
            if (dueToday > 0) insights.add("You have $dueToday bills due today.")
            if (overdue > 0) insights.add("You have $overdue overdue bills.")
            insights.add("You spent ₱${String.format(Locale.getDefault(), "%.2f", totalPaid)} this month.")
            insights.add("Your net cash flow is ₱${String.format(Locale.getDefault(), "%.2f", netCashFlow)}.")
            
            byCategory.maxByOrNull { it.value }?.key?.let {
                insights.add("Your largest expense is ${it.name}.")
            }

            DashboardData(
                totalBillsThisMonth = totalDue,
                totalPaidThisMonth = totalPaid,
                totalRemaining = remaining,
                totalIncomeThisMonth = totalIncomeMonth,
                totalIncomeThisYear = totalIncomeYear,
                netCashFlow = netCashFlow,
                netBalance = netBalance,
                billsDueToday = dueToday,
                upcomingBillsCount = monthCycles.count { it.status != BillStatus.PAID },
                overdueBillsCount = overdue,
                upcomingBills = upcoming,
                recentPayments = recentPayments,
                spendingTrend = trend,
                spendingByCategory = byCategory,
                incomeByCategory = incomeByCategory,
                insights = insights
            )
        }
    }
}
