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

    fun billDetail(billId: Long) = "bill_detail/$billId"
    fun paymentSheet(billCycleId: Long) = "payment_sheet/$billCycleId"
    fun paymentHistory() = "payment_history"
    fun paymentDetail(paymentId: Long) = "payment_detail/$paymentId"
}
