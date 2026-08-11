package com.example.nestworth.achievement

enum class AchievementCriteriaType {
    XP_LEVEL_REACHED,
    NET_WORTH_REACHED,
    PROFILE_CREATED,
    STREAK_DAYS
}

data class AchievementCriteria(
    val type: AchievementCriteriaType,
    val threshold: Long = 0
)

data class Achievement(
    val id: Int,
    val title: String,
    val description: String,
    val emoji: String,
    val color: String,
    val criteria: AchievementCriteria
)