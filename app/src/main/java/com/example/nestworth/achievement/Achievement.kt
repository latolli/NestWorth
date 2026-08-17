package com.example.nestworth.achievement

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
    val rankRoman: String = ""
)