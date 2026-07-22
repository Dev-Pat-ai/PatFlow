package com.patflow.app.domain.usecase.dashboard

import com.patflow.app.domain.model.BillStatus
import com.patflow.app.domain.model.BillWithCycle
import com.patflow.app.domain.model.DashboardData
import com.patflow.app.domain.model.Category
import com.patflow.app.domain.repository.BillRepository
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

class GetDashboardDataUseCase @Inject constructor(
    private val billRepository: BillRepository,
    private val paymentRepository: PaymentRepository
) {
    operator fun invoke(): Flow<DashboardData> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val startOfMonth = LocalDate(now.year, now.month, 1)
        val endOfMonth = startOfMonth.plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
        val sixMonthsAgo = startOfMonth.minus(DatePeriod(months = 5))

        return combine(
            billRepository.getBillsWithCycles(),
            billRepository.getCyclesByDateRange(startOfMonth.toString(), endOfMonth.toString()),
            paymentRepository.getPayments()
        ) { allBills, monthCycles, allPayments ->
            
            val totalDue = monthCycles.sumOf { it.amountDue }
            val totalPaid = monthCycles.sumOf { it.amountPaid }
            val remaining = (totalDue - totalPaid).coerceAtLeast(0.0)
            
            val dueToday = monthCycles.count { it.dueDate == now && it.status != BillStatus.PAID }
            val overdue = monthCycles.count { it.status == BillStatus.OVERDUE }
            
            val upcoming = monthCycles
                .filter { it.status != BillStatus.PAID }
                .sortedBy { it.dueDate }
                .take(5)
                .mapNotNull { cycle ->
                    val bill = allBills.find { it.bill.id == cycle.billId }?.bill
                    bill?.let { BillWithCycle(it, cycle) }
                }

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

            // Insights
            val insights = mutableListOf<String>()
            if (dueToday > 0) insights.add("You have $dueToday bills due today.")
            if (overdue > 0) insights.add("You have $overdue overdue bills.")
            insights.add("You spent ₱${String.format(Locale.getDefault(), "%.2f", totalPaid)} this month.")
            
            val topCategory = byCategory.maxByOrNull { it.value }?.key
            if (topCategory != null) insights.add("Your largest expense is ${topCategory.name}.")

            DashboardData(
                totalBillsThisMonth = totalDue,
                totalPaidThisMonth = totalPaid,
                totalRemaining = remaining,
                billsDueToday = dueToday,
                upcomingBillsCount = monthCycles.count { it.status != BillStatus.PAID },
                overdueBillsCount = overdue,
                upcomingBills = upcoming,
                recentPayments = recentPayments,
                spendingTrend = trend,
                spendingByCategory = byCategory,
                insights = insights
            )
        }
    }
}
