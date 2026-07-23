package com.patflow.app.data.local.database

import com.patflow.app.data.local.entity.BillCategoryEntity
import com.patflow.app.data.local.entity.IncomeCategoryEntity

/**
 * Design System §10 — Category Branding.
 * Fixed predefined categories seeded on first database creation.
 */
object DatabaseSeeder {

    fun getPredefinedCategories(): List<BillCategoryEntity> = listOf(
        BillCategoryEntity(name = "Electricity", iconKey = "bolt", colorHex = "#8C5A00"),
        BillCategoryEntity(name = "Water", iconKey = "water_drop", colorHex = "#00658F"),
        BillCategoryEntity(name = "Internet", iconKey = "wifi", colorHex = "#3D4E85"),
        BillCategoryEntity(name = "Rent", iconKey = "house", colorHex = "#6B4F1C"),
        BillCategoryEntity(name = "Phone", iconKey = "smartphone", colorHex = "#5C5F00"),
        BillCategoryEntity(name = "Insurance", iconKey = "shield", colorHex = "#00658F"),
        BillCategoryEntity(name = "Tuition", iconKey = "school", colorHex = "#7D2E68"),
        BillCategoryEntity(name = "Subscription", iconKey = "subscriptions", colorHex = "#8C3B00"),
        BillCategoryEntity(name = "Loan", iconKey = "account_balance", colorHex = "#BA1A1A"),
        BillCategoryEntity(name = "Savings", iconKey = "savings", colorHex = "#2E8B57"),
        BillCategoryEntity(name = "HOA Fees", iconKey = "apartment", colorHex = "#5B5D72")
    )

    fun getPredefinedIncomeCategories(): List<IncomeCategoryEntity> = listOf(
        IncomeCategoryEntity(name = "Salary", iconKey = "work", colorHex = "#2E8B57"),
        IncomeCategoryEntity(name = "Freelance", iconKey = "laptop_mac", colorHex = "#00658F"),
        IncomeCategoryEntity(name = "Business", iconKey = "store", colorHex = "#8C5A00"),
        IncomeCategoryEntity(name = "Allowance", iconKey = "child_care", colorHex = "#3D4E85"),
        IncomeCategoryEntity(name = "Bonus", iconKey = "celebration", colorHex = "#BA1A1A"),
        IncomeCategoryEntity(name = "Commission", iconKey = "trending_up", colorHex = "#6B4F1C"),
        IncomeCategoryEntity(name = "Investment", iconKey = "account_balance_wallet", colorHex = "#7D2E68"),
        IncomeCategoryEntity(name = "Cashback", iconKey = "rebase_edit", colorHex = "#8C3B00"),
        IncomeCategoryEntity(name = "Refund", iconKey = "undo", colorHex = "#5B5D72"),
        IncomeCategoryEntity(name = "Gift", iconKey = "card_giftcard", colorHex = "#F2A93B"),
        IncomeCategoryEntity(name = "Other", iconKey = "add_circle", colorHex = "#46464F")
    )
}
