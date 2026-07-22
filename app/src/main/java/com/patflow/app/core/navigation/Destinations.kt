package com.patflow.app.core.navigation

/**
 * Screen graph destinations (Architecture §6). Routes are stubbed here;
 * each feature module wires its own composable(route) { ... } into
 * NavGraph.kt as that feature is built — starting with Bills next.
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
    const val PAYMENT_HISTORY = "payment_history/{billId}"

    fun billDetail(billId: Long) = "bill_detail/$billId"
    fun paymentSheet(billCycleId: Long) = "payment_sheet/$billCycleId"
    fun paymentHistory(billId: Long) = "payment_history/$billId"
}
