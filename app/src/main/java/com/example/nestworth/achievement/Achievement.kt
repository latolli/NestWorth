package com.example.nestworth.achievement

import com.example.nestworth.Repository.settings.Currency
import com.example.nestworth.core.formatMoney

enum class AchievementCriteriaType {
    XP_LEVEL_REACHED,
    NET_WORTH_REACHED,
    STARTING_STEPS,
    STREAK_DAYS,
    LOGGED_EVENTS
}

data class AchievementCriteria(
    val type: AchievementCriteriaType,
    val threshold: Int = 0
)

data class Achievement(
    val id: Int,
    val title: String,
    val description: String,
    val emoji: String,
    val color: String,
    val criteria: AchievementCriteria,
    val rank: Int = 0,
    val rankRoman: String = "",
    val descriptionFormatter: (Currency) -> String = { description }
) {
    fun formatDescription(currency: Currency): String = descriptionFormatter(currency)
}