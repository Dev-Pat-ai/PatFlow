package com.patflow.app.core.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.patflow.app.domain.model.IncomeEntry
import com.patflow.app.domain.model.RecurrenceType
import com.patflow.app.domain.repository.IncomeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

/**
 * Periodically checks for recurring income sources and generates entries (Architecture §Phase 9).
 */
@HiltWorker
class RecurringIncomeWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: IncomeRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val sources = repository.getSources().first()

        for (source in sources) {
            if (!source.isActive || source.isDeleted) continue
            
            val entries = repository.getEntries().first()
            val lastEntry = entries.filter { it.entry.incomeSourceId == source.id }
                .maxByOrNull { it.entry.entryDate }
            
            val nextDate = if (lastEntry == null) {
                source.recurrence.startDate
            } else {
                calculateNextDate(lastEntry.entry.entryDate, source.recurrence.type, source.recurrence.interval)
            }

            if (nextDate <= now.date) {
                repository.insertEntry(
                    IncomeEntry(
                        incomeSourceId = source.id,
                        category = source.category,
                        amount = source.defaultAmount,
                        entryDate = nextDate,
                        createdAt = now
                    )
                )
            }
        }

        return Result.success()
    }

    private fun calculateNextDate(lastDate: kotlinx.datetime.LocalDate, type: RecurrenceType, interval: Int): kotlinx.datetime.LocalDate {
        return when (type) {
            RecurrenceType.ONE_TIME -> lastDate // Should not happen for active sources
            RecurrenceType.WEEKLY -> lastDate.plus(DatePeriod(days = 7 * interval))
            RecurrenceType.BIWEEKLY -> lastDate.plus(DatePeriod(days = 14 * interval))
            RecurrenceType.MONTHLY -> lastDate.plus(DatePeriod(months = interval))
            RecurrenceType.QUARTERLY -> lastDate.plus(DatePeriod(months = 3 * interval))
            RecurrenceType.YEARLY -> lastDate.plus(DatePeriod(years = interval))
            RecurrenceType.CUSTOM_DAYS -> lastDate.plus(DatePeriod(days = interval))
        }
    }
}
