package com.patflow.app.core.navigation

/**
 * Screen graph destinations and route factory methods (Architecture §6).
 * Routes are consumed by NavGraph.kt to wire feature screens.
 */
object Destinations {
    const val DASHBOARD = "dashboard"
    const val MONEY = "money"
    const val CALENDAR = "calendar"
    const val REPORTS = "reports"
    const val SETTINGS = "settings"

    const val BILL_DETAIL = "bill_detail/{billId}"
    const val ADD_EDIT_BILL = "add_edit_bill"
    const val PAYMENT_HISTORY = "payment_history"
    const val PAYMENT_DETAIL = "payment_detail/{paymentId}"

    const val INCOME_SOURCES = "income_sources"
    const val ADD_EDIT_INCOME = "add_edit_income"
    const val ADD_EDIT_INCOME_SOURCE = "add_edit_income_source"

    const val BUDGET_DETAIL = "budget_detail/{budgetId}"
    const val ADD_EDIT_BUDGET = "add_edit_budget"

    const val SAVINGS_GOAL_DETAIL = "savings_goal_detail/{goalId}"
    const val ADD_EDIT_SAVINGS_GOAL = "add_edit_savings_goal"

    fun billDetail(billId: Long) = "bill_detail/$billId"
    fun paymentDetail(paymentId: Long) = "payment_detail/$paymentId"
    fun incomeSources() = "income_sources"
    fun addEditIncome(entryId: Long? = null) = if (entryId != null) "add_edit_income?entryId=$entryId" else "add_edit_income"
    fun addEditIncomeSource(sourceId: Long? = null) = if (sourceId != null) "add_edit_income_source?sourceId=$sourceId" else "add_edit_income_source"
    fun addEditBudget(budgetId: Long? = null) = if (budgetId != null) "add_edit_budget?budgetId=$budgetId" else "add_edit_budget"
    fun budgetDetail(budgetId: Long) = "budget_detail/$budgetId"
    fun addEditSavingsGoal(goalId: Long? = null) = if (goalId != null) "add_edit_savings_goal?goalId=$goalId" else "add_edit_savings_goal"
    fun savingsGoalDetail(goalId: Long) = "savings_goal_detail/$goalId"
}
