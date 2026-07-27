package com.example.nestworth.achievement

import com.example.nestworth.Repository.model.Profile
import com.example.nestworth.ui.viewmodel.MainViewModel

object AchievementEvaluator {

    fun evaluateAchievements(profile: Profile, netWorth: Double): AchievementResult {
        val newIds = getNewlyUnlocked(profile, netWorth).toMutableList()

        val newXp = newIds.size * 100
        var leveledUpProfile = profile.copy(xpAmount = profile.xpAmount + newXp)
        if ((profile.xpAmount + newXp) % 5000 < newXp) {
            leveledUpProfile = leveledUpProfile.copy(xpLevel = profile.xpLevel + 1)
            newIds += getNewlyUnlocked(leveledUpProfile, netWorth)
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