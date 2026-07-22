package com.patflow.app.domain.model

/**
 * Domain model for bill/income categories.
 */
data class Category(
    val id: Long,
    val name: String,
    val iconKey: String,
    val colorHex: String,
    val isCustom: Boolean = false
)
