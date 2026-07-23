package com.patflow.app.data.mapper

import com.patflow.app.data.local.entity.ReminderEntity
import com.patflow.app.domain.model.Reminder

fun ReminderEntity.toDomain(): Reminder = Reminder(
    id = id,
    billCycleId = billCycleId,
    remindAt = remindAt,
    isSent = isSent,
    offsetDays = offsetDays
)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id = id,
    billCycleId = billCycleId,
    remindAt = remindAt,
    isSent = isSent,
    offsetDays = offsetDays
)
