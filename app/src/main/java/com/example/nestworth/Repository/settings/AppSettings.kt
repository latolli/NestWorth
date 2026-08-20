package com.example.nestworth.Repository.settings

enum class ThemeMode {
    LIGHT,
    DARK
}

enum class Currency {
    EUR,
    USD,
    GBP,
    PERCENTAGE  // Can't be set as currency, only used for formatMoney function
}

enum class TimeRange(
    val months: Int?,
    val label: String
) {
    MONTH(1, "1 mo"),
    MONTHS_3(3, "3 mo"),
    MONTHS_6(6, "6 mo"),
    YEAR(12, "1 yr"),
    MAX(null, "Max");

    fun cutoffTime(currentTime: Long): Long {
        return months?.let {
            currentTime - (it * 30L * 24 * 60 * 60 * 1000)
        } ?: 0L
    }
}


data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.DARK,
    val currency: Currency = Currency.EUR,
    val timeRange: TimeRange = TimeRange.MAX
)
