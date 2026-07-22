package com.patflow.app.data.local.database

import com.patflow.app.data.local.entity.BillCategoryEntity

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
}
