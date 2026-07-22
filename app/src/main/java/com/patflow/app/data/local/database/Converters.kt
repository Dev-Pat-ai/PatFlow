package com.patflow.app.data.local.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * Room TypeConverters — Architecture §10 chose kotlinx-datetime over
 * java.time directly in the domain layer, and §8.3+ stores dates/datetimes
 * as ISO-8601 TEXT columns. These converters bridge kotlinx.datetime types
 * to the TEXT representation Room persists.
 */
class Converters {

    // ---- kotlinx.datetime.LocalDate <-> ISO date string ("2026-07-22") ----
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    // ---- kotlinx.datetime.LocalDateTime <-> ISO datetime string ----
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? = dateTime?.toString()

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }
}
