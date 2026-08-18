package com.example.nestworth.Repository.settings

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

enum class Currency {
    EUR,
    USD,
    GBP,
    PERCENTAGE  // Can't be set as currency, only used for formatMoney function
}

data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.DARK,
    val currency: Currency = Currency.EUR
)
