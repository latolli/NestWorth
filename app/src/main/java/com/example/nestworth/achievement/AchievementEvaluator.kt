package com.example.nestworth.achievement

import com.example.nestworth.Repository.model.Profile
import com.example.nestworth.core.Constants.INVALID_DOUBLE
import com.example.nestworth.core.Constants.XP_PER_ACHIEVEMENT
import com.example.nestworth.core.Constants.XP_PER_LEVEL

object AchievementEvaluator {

    fun evaluateAchievements(profile: Profile, netWorth: Double): AchievementResult {
        val newIds = getNewlyUnlocked(profile, netWorth).toMutableList()

        val xpFromInitialAchievements = newIds.size * XP_PER_ACHIEVEMENT
        val totalXpAfterInitial = profile.xpAmount + xpFromInitialAchievements
        var leveledUpProfile = profile.copy(xpAmount = totalXpAfterInitial)

        // Check if level up was caused by new achievements
        val newLevel = totalXpAfterInitial / XP_PER_LEVEL
        if (newLevel > profile.xpLevel) {
            leveledUpProfile = leveledUpProfile.copy(xpLevel = newLevel)

            // Pass net worth as invalid to avoid double-counting
            val leveledUpNewIds = getNewlyUnlocked(leveledUpProfile, INVALID_DOUBLE)

            // Check if level up unlocked new achievements that aren't taken into account yet
            var totalXpAfterLevelUp = leveledUpProfile.xpAmount
            for (id in leveledUpNewIds) {
                if (id !in newIds) {
                    newIds += id
                    totalXpAfterLevelUp += XP_PER_ACHIEVEMENT
                }
            }
            leveledUpProfile = leveledUpProfile.copy(xpAmount = totalXpAfterLevelUp)
        }

        val updatedProfile = leveledUpProfile.copy(achievements = profile.achievements + newIds)
        return AchievementResult(updatedProfile, newIds)
    }

    private fun getNewlyUnlocked(
        profile: Profile,
        netWorth: Double
    ): List<Int> {
        val alreadyUnlocked = profile.achievements.toSet()

        return AchievementCatalog.ALL
            .filter { it.id !in alreadyUnlocked }
            .filter { meetsCriteria(it.criteria, profile, netWorth) }
            .map { it.id }
    }

    private fun meetsCriteria(
        criteria: AchievementCriteria,
        profile: Profile,
        netWorth: Double
    ): Boolean = when (criteria.type) {
        AchievementCriteriaType.PROFILE_CREATED -> true
        AchievementCriteriaType.XP_LEVEL_REACHED -> profile.xpLevel >= criteria.threshold
        AchievementCriteriaType.NET_WORTH_REACHED -> netWorth >= criteria.threshold
        AchievementCriteriaType.STREAK_DAYS -> profile.dailyStreak >= criteria.threshold
    }
}

data class AchievementResult(val updatedProfile: Profile, val newIds: List<Int>)