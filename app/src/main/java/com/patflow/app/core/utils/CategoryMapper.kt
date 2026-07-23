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
            CategoryType.valueOf(name.uppercase().replace(" ", "_"))
        } catch (_: Exception) {
            CategoryType.ELECTRICITY
        }
    }
}
