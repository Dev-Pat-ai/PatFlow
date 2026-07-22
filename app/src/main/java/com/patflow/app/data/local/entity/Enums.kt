package com.patflow.app.data.local.entity

/** Bill/income recurrence pattern (Architecture §8.3). Shared by `bill` and `income_source`. */
enum class RecurrenceType {
    ONE_TIME,
    WEEKLY,
    MONTHLY,
    YEARLY,
    CUSTOM_DAYS,
}

/** Bill cycle status (Architecture §8.3 / FR-2.3). Derived by domain logic, never free-typed. */
enum class BillCycleStatus {
    UNPAID,
    PARTIALLY_PAID,
    PAID,
    OVERDUE,
}

/** Payment method (Architecture §8.3). */
enum class PaymentMethod {
    CASH,
    BANK_TRANSFER,
    EWALLET,
    CARD,
    OTHER,
}

/**
 * v2 sync scaffold status (Architecture §8.1 — every table has a nullable
 * sync scaffold so v2 cloud sync doesn't require a schema migration touching
 * core columns). Unused in v1 beyond the default value.
 */
enum class SyncStatus {
    LOCAL_ONLY,
    SYNCED,
    PENDING,
    CONFLICT,
}
