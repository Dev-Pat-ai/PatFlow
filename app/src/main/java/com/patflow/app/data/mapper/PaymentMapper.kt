package com.patflow.app.data.mapper

import com.patflow.app.data.local.entity.PaymentEntity
import com.patflow.app.domain.model.Payment
import com.patflow.app.domain.model.PaymentMethod
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun PaymentEntity.toDomain(): Payment = Payment(
    id = id,
    billCycleId = billCycleId,
    amount = amount,
    paymentDate = paymentDate,
    method = PaymentMethod.valueOf(method),
    note = note,
    createdAt = createdAt
)

fun Payment.toEntity(): PaymentEntity = PaymentEntity(
    id = id,
    billCycleId = billCycleId,
    amount = amount,
    paymentDate = paymentDate,
    method = method.name,
    note = note,
    createdAt = createdAt
)
