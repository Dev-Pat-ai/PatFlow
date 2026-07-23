package com.patflow.app.core.navigation

/**
 * Screen graph destinations and route factory methods (Architecture §6).
 * Routes are consumed by NavGraph.kt to wire feature screens.
 */
object Destinations {
    const val ONBOARDING = "onboarding"
    const val SECURITY_LOCK = "security_lock"
    const val DASHBOARD = "dashboard"
    const val MONEY = "money" // segmented: bills | income | savings
    const val CALENDAR = "calendar" // segmented: calendar | timeline
    const val SEARCH = "search"
    const val REPORTS = "reports"
    const val SETTINGS = "settings"

    const val BILL_DETAIL = "bill_detail/{billId}"
    const val ADD_EDIT_BILL = "add_edit_bill" // ?billId= via optional query arg
    const val PAYMENT_SHEET = "payment_sheet/{billCycleId}"
    const val PAYMENT_HISTORY = "payment_history"
    const val PAYMENT_DETAIL = "payment_detail/{paymentId}"

    const val INCOME_LIST = "income_list"
    const val INCOME_SOURCES = "income_sources"
    const val ADD_EDIT_INCOME = "add_edit_income" // ?entryId= via optional query arg
    const val ADD_EDIT_INCOME_SOURCE = "add_edit_income_source" // ?sourceId=

    fun billDetail(billId: Long) = "bill_detail/$billId"
    fun paymentSheet(billCycleId: Long) = "payment_sheet/$billCycleId"
    fun paymentHistory() = "payment_history"
    fun paymentDetail(paymentId: Long) = "payment_detail/$paymentId"
    fun incomeList() = "income_list"
    fun incomeSources() = "income_sources"
    fun addEditIncome(entryId: Long? = null) = if (entryId != null) "add_edit_income?entryId=$entryId" else "add_edit_income"
    fun addEditIncomeSource(sourceId: Long? = null) = if (sourceId != null) "add_edit_income_source?sourceId=$sourceId" else "add_edit_income_source"
}
