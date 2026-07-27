package com.example.nestworth.achievement

object AchievementCatalog {
    val ALL: List<Achievement> = listOf(
        Achievement(
            id = 1,
            title = "First Steps",
            description = "Create your profile",
            criteria = AchievementCriteria(AchievementCriteriaType.PROFILE_CREATED)
        ),
        Achievement(
            id = 2,
            title = "Getting Started",
            description = "Reach XP level 2",
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 2)
        ),
        Achievement(
            id = 3,
            title = "First $10K",
            description = "Reach a net worth of $10,000",
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 10_000)
        ),
        Achievement(
            id = 4,
            title = "First $20K",
            description = "Reach a net worth of $20,000",
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 20_000)
        ),
        Achievement(
            id = 5,
            title = "First $30K",
            description = "Reach a net worth of $30,000",
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 30_000)
        ),
        Achievement(
            id = 6,
            title = "First $40K",
            description = "Reach a net worth of $40,000",
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 40_000)
        ),
        Achievement(
            id = 7,
            title = "First $40K",
            description = "Reach a net worth of $40,000",
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 50_000)
        ),
    )

    val byId: Map<Int, Achievement> = ALL.associateBy { it.id }
}