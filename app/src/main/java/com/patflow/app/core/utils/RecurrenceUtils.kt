package com.patflow.app.core.utils

import com.patflow.app.domain.model.RecurrenceType
import kotlinx.datetime.*

/**
 * Utility for calculating future dates based on recurrence patterns (Architecture §1.1).
 */
object RecurrenceUtils {

    /**
     * Calculates the next occurrence date based on the last date and recurrence type.
     */
    fun calculateNextDate(
        lastDate: LocalDate,
        type: RecurrenceType,
        interval: Int = 1
    ): LocalDate {
        return when (type) {
            RecurrenceType.ONE_TIME -> lastDate
            RecurrenceType.WEEKLY -> lastDate.plus(DatePeriod(days = 7 * interval))
            RecurrenceType.BIWEEKLY -> lastDate.plus(DatePeriod(days = 14 * interval))
            RecurrenceType.MONTHLY -> lastDate.plus(DatePeriod(months = interval))
            RecurrenceType.QUARTERLY -> lastDate.plus(DatePeriod(months = 3 * interval))
            RecurrenceType.YEARLY -> lastDate.plus(DatePeriod(years = interval))
            RecurrenceType.CUSTOM_DAYS -> lastDate.plus(DatePeriod(days = interval))
        }
    }
}
