package com.example.nestworth.core

import com.example.nestworth.Repository.settings.Currency
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

fun formatMoney(
    value: Double,
    currency: Currency = Currency.EUR,
    decimalPlaces: Int = 0
): String {
    require(decimalPlaces >= 0) {
        "decimalPlaces must be >= 0"
    }

    // Percentage
    if (currency == Currency.PERCENTAGE) {
        if (abs(value) >= 1_000)
        {
            return "${String.format(
                Locale.getDefault(),
                "%.${decimalPlaces}fk",
                value / 1_000)}%"
        }
        else {
            return String.format(
                Locale.getDefault(),
                "%.${decimalPlaces}f%%",
                value
            )
        }
    }

    // Currency
    val javaCurrency = when (currency) {
        Currency.EUR -> java.util.Currency.getInstance("EUR")
        Currency.USD -> java.util.Currency.getInstance("USD")
        Currency.GBP -> java.util.Currency.getInstance("GBP")
        Currency.PERCENTAGE -> error("Handled above")
    }

    // Millions
    if (abs(value) >= 1_000_000) {
        val millions = value / 1_000_000.0

        return formatAbbreviated(
            value = millions,
            suffix = "M",
            currency = currency
        )
    }

    // Thousands
    if (abs(value) >= 100_000) {
        val thousands = value / 1_000.0

        return formatAbbreviated(
            value = thousands,
            suffix = "k",
            currency = currency
        )
    }

    // Normal currency value
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
        this.currency = javaCurrency
        minimumFractionDigits = decimalPlaces
        maximumFractionDigits = decimalPlaces
    }

    return formatter.format(value)
}

private fun formatAbbreviated(
    value: Double,
    suffix: String,
    currency: Currency
): String {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = if (value % 1.0 == 0.0) 0 else 1
        maximumFractionDigits = 1
    }

    val number = formatter.format(value)

    val symbol = when (currency) {
        Currency.EUR -> "€"
        Currency.USD -> "$"
        Currency.GBP -> "£"
        Currency.PERCENTAGE -> error("Not a currency")
    }

    return "$symbol$number$suffix"
}