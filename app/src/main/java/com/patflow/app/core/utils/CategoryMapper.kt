package com.patflow.app.core.utils

import com.patflow.app.core.components.CategoryType

/**
 * Utility to map raw category names from the database to [CategoryType] enum (Architecture §1.14).
 * Centralizes mapping logic to avoid duplication across feature modules.
 */
object CategoryMapper {

    /**
     * Maps a category name to a [CategoryType].
     * Defaults to [CategoryType.ELECTRICITY] if the name is not recognized.
     * 
     * @param name The name of the category (e.g., "Electricity", "Water").
     * @return The corresponding [CategoryType].
     */
    fun mapToType(name: String): CategoryType {
        return try {
            val normalized = name.uppercase().replace(" ", "_")
            when (normalized) {
                "CASH_BACK" -> CategoryType.CASHBACK
                else -> CategoryType.valueOf(normalized)
            }
        } catch (_: Exception) {
            CategoryType.OTHER
        }
    }
}
