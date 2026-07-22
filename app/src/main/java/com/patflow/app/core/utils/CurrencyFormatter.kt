package com.patflow.app.core.utils

import android.icu.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * Design System §11 — Currency & Localization.
 *
 * Formats amounts according to the device locale while respecting the
 * provided currency code (defaulting to PHP).
 */
object CurrencyFormatter {

    private const val DEFAULT_CURRENCY_CODE = "PHP"

    /**
     * Formats a numeric amount into a currency string.
     *
     * @param amount The value to format.
     * @param currencyCode The ISO 4217 currency code (e.g., "PHP", "USD").
     * @param locale The locale to use for number formatting (thousands/decimal separators).
     */
    fun formatAmount(
        amount: Double,
        currencyCode: String = DEFAULT_CURRENCY_CODE,
        locale: Locale = Locale.getDefault()
    ): String {
        return try {
            val format = NumberFormat.getCurrencyInstance(locale)
            format.currency = android.icu.util.Currency.getInstance(currencyCode)
            format.format(amount)
        } catch (e: Exception) {
            // Fallback for cases where ICU might fail or currency code is invalid
            val currency = Currency.getInstance(currencyCode)
            val symbol = currency.getSymbol(locale)
            "%.2f %s".format(locale, amount, symbol)
        }
    }

    /**
     * Returns the currency symbol for a given code and locale.
     */
    fun getSymbol(
        currencyCode: String = DEFAULT_CURRENCY_CODE,
        locale: Locale = Locale.getDefault()
    ): String {
        return try {
            Currency.getInstance(currencyCode).getSymbol(locale)
        } catch (e: Exception) {
            "₱" // Absolute fallback for PatFlow's default
        }
    }
}
