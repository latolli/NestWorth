package com.example.nestworth.core

import com.example.nestworth.Repository.settings.Currency
import java.text.NumberFormat
import java.util.Locale

fun formatMoney(
    value: Double,
    currency: Currency = Currency.EUR
): String {

    if (currency == Currency.PERCENTAGE) {
        return String.format(
            Locale.getDefault(),
            "%.1f %%",
            value
        )
    }

    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    formatter.currency = when (currency) {
        Currency.EUR -> java.util.Currency.getInstance("EUR")
        Currency.USD -> java.util.Currency.getInstance("USD")
        Currency.GBP -> java.util.Currency.getInstance("GBP")
        Currency.PERCENTAGE -> error("Handled above")
    }

    return formatter.format(value)
}