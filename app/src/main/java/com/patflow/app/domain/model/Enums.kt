package com.patflow.app.domain.model

/** Bill/income recurrence pattern (Architecture §8.3). Shared by `bill` and `income_source`. */
enum class RecurrenceType {
    ONE_TIME,
    WEEKLY,
    BIWEEKLY,
    MONTHLY,
    QUARTERLY,
    YEARLY,
    CUSTOM_DAYS,
}

/** Bill cycle status (Architecture §8.3 / FR-2.3). Derived by domain logic, never free-typed. */
enum class BillStatus {
    UNPAID,
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
 * v2 sync scaffold status (Architecture §8.1).
 */
enum class SyncStatus {
    LOCAL_ONLY,
    SYNCED,
    PENDING,
    CONFLICT,
}

enum class IncomeSortOrder {
    NEWEST,
    OLDEST,
    HIGHEST_AMOUNT,
    LOWEST_AMOUNT,
    ALPHABETICAL
}

enum class BudgetType {
    MONTHLY,
    WEEKLY,
    YEARLY,
    CATEGORY,
    CUSTOM
}
