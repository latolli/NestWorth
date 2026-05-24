package com.example.nestworth.ui.utils

import java.util.Locale

fun FormatMoney(
    value: Double,
    dpFormat: String = "%.0f",
    unitText: String = " €"
): String {
    // TODO: Add support for other currencies
    var returnString = "${String.format(Locale.getDefault(), dpFormat, value)}${unitText}"
    if (value >= 100000)
    {
        val k = value / 1000.0
        if (k == Math.floor(k))
            returnString = "${String.format(Locale.getDefault(), "%.0fk", k)}${unitText}"
        else
            returnString = "${String.format(Locale.getDefault(), "%.1fk", k)}${unitText}"
    }
    return returnString
}