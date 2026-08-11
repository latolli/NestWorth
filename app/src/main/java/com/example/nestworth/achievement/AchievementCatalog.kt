package com.example.nestworth.achievement

object AchievementCatalog {

    private fun emojiFor(type: AchievementCriteriaType): String = when (type) {
        AchievementCriteriaType.PROFILE_CREATED -> "👤"
        AchievementCriteriaType.XP_LEVEL_REACHED -> "⭐"
        AchievementCriteriaType.NET_WORTH_REACHED -> "💰"
        AchievementCriteriaType.STREAK_DAYS -> "🔥"
    }

    private fun colorFor(type: AchievementCriteriaType): String = when (type) {
        AchievementCriteriaType.PROFILE_CREATED -> "#6F2DA8" // Purple
        AchievementCriteriaType.XP_LEVEL_REACHED -> "#EDC001" // Yellow
        AchievementCriteriaType.NET_WORTH_REACHED -> "#EFBF04" // Gold
        AchievementCriteriaType.STREAK_DAYS -> "#FF4D00" // Orange
    }

    val ALL: List<Achievement> = listOf(
        // --- Onboarding ---
        Achievement(
            id = 1,
            title = "First Steps",
            description = "Create your profile",
            emoji = emojiFor(AchievementCriteriaType.PROFILE_CREATED),
            color = colorFor(AchievementCriteriaType.PROFILE_CREATED),
            criteria = AchievementCriteria(AchievementCriteriaType.PROFILE_CREATED)
        ),

        // --- XP Level milestones (up to level 100) ---
        Achievement(
            id = 2,
            title = "Getting Started",
            description = "Reach XP level 2",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 2)
        ),
        Achievement(
            id = 3,
            title = "Novice",
            description = "Reach XP level 5",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 5)
        ),
        Achievement(
            id = 4,
            title = "Apprentice",
            description = "Reach XP level 10",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 10)
        ),
        Achievement(
            id = 5,
            title = "Skilled",
            description = "Reach XP level 20",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 20)
        ),
        Achievement(
            id = 6,
            title = "Proficient",
            description = "Reach XP level 30",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 30)
        ),
        Achievement(
            id = 7,
            title = "Expert",
            description = "Reach XP level 40",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 40)
        ),
        Achievement(
            id = 8,
            title = "Halfway There",
            description = "Reach XP level 50",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 50)
        ),
        Achievement(
            id = 9,
            title = "Veteran",
            description = "Reach XP level 60",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 60)
        ),
        Achievement(
            id = 10,
            title = "Elite",
            description = "Reach XP level 70",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 70)
        ),
        Achievement(
            id = 11,
            title = "Master",
            description = "Reach XP level 80",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 80)
        ),
        Achievement(
            id = 12,
            title = "Grandmaster",
            description = "Reach XP level 90",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 90)
        ),
        Achievement(
            id = 13,
            title = "Legend",
            description = "Reach XP level 100",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 100)
        ),

        // --- Net worth milestones (up to $1,000,000) ---
        Achievement(
            id = 14,
            title = "First $10K",
            description = "Reach a net worth of $10,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 10_000)
        ),
        Achievement(
            id = 15,
            title = "$25K Club",
            description = "Reach a net worth of $25,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 25_000)
        ),
        Achievement(
            id = 16,
            title = "$50K Milestone",
            description = "Reach a net worth of $50,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 50_000)
        ),
        Achievement(
            id = 17,
            title = "Six Figures",
            description = "Reach a net worth of $100,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 100_000)
        ),
        Achievement(
            id = 18,
            title = "Quarter Millionaire",
            description = "Reach a net worth of $250,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 250_000)
        ),
        Achievement(
            id = 19,
            title = "Half Millionaire",
            description = "Reach a net worth of $500,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 500_000)
        ),
        Achievement(
            id = 20,
            title = "$750K Milestone",
            description = "Reach a net worth of $750,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 750_000)
        ),
        Achievement(
            id = 21,
            title = "Millionaire",
            description = "Reach a net worth of $1,000,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 1_000_000)
        ),

        // --- Streak milestones (up to 365 days) ---
        Achievement(
            id = 22,
            title = "Warming Up",
            description = "Reach a 3 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 3)
        ),
        Achievement(
            id = 23,
            title = "One Week Strong",
            description = "Reach a 7 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 7)
        ),
        Achievement(
            id = 24,
            title = "Two Weeks In",
            description = "Reach a 14 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 14)
        ),
        Achievement(
            id = 25,
            title = "One Month Streak",
            description = "Reach a 30 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 30)
        ),
        Achievement(
            id = 26,
            title = "Two Months Strong",
            description = "Reach a 60 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 60)
        ),
        Achievement(
            id = 27,
            title = "Quarter Year Streak",
            description = "Reach a 90 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 90)
        ),
        Achievement(
            id = 28,
            title = "Half Year Streak",
            description = "Reach a 180 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 180)
        ),
        Achievement(
            id = 29,
            title = "One Year Streak",
            description = "Reach a 365 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 365)
        ),
    )

    val byId: Map<Int, Achievement> = ALL.associateBy { it.id }
}