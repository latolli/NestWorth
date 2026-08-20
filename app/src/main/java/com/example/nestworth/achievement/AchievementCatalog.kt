package com.example.nestworth.achievement

import com.example.nestworth.core.formatMoney

enum class StartingStep(val mask: Int) {
    PROFILE_CREATED(1 shl 0),
    CURRENCY_SELECTED(1 shl 1),
    PROFILE_PICTURE_CHANGED(1 shl 2),
    FIRST_ASSET_CREATED(1 shl 3)
}

object AchievementCatalog {

    private fun emojiFor(type: AchievementCriteriaType): String = when (type) {
        AchievementCriteriaType.STARTING_STEPS -> "🌱"
        AchievementCriteriaType.XP_LEVEL_REACHED -> "🎖️"
        AchievementCriteriaType.NET_WORTH_REACHED -> "💰"
        AchievementCriteriaType.STREAK_DAYS -> "🔥"
        AchievementCriteriaType.LOGGED_EVENTS -> "⏳"
    }
    // Unused good emojis: 🪙, 💵, ⚡️, 🎩, 🎯, ⭐

    private fun colorFor(type: AchievementCriteriaType): String = when (type) {
        AchievementCriteriaType.STARTING_STEPS -> "#4B6043" // Green
        AchievementCriteriaType.XP_LEVEL_REACHED -> "#C49102" // Yellow
        AchievementCriteriaType.NET_WORTH_REACHED -> "#EFBF04" // Gold
        AchievementCriteriaType.STREAK_DAYS -> "#FF4D00" // Orange
        AchievementCriteriaType.LOGGED_EVENTS -> "#008000" // Green
    }

    // Converts 1, 2, 3... into "I", "II", "III"... Supports up to 3999,
    // which is far beyond any realistic achievement count per category.
    private fun toRoman(number: Int): String {
        val values = intArrayOf(10, 9, 5, 4, 1)
        val symbols = arrayOf("X", "IX", "V", "IV", "I")
        var remaining = number
        val sb = StringBuilder()
        for (i in values.indices) {
            while (remaining >= values[i]) {
                sb.append(symbols[i])
                remaining -= values[i]
            }
        }
        return sb.toString()
    }

    // Defined without "rank" — it's derived automatically below, based on
    // each achievement's position within its own criteria type.
    // Current max ID: 50
    private val UNRANKED: List<Achievement> = listOf(
        // --- Onboarding ---
        // Onboarding thresholds should follow binary counting
        Achievement(
            id = 1,
            title = "First Steps",
            description = "Create your profile",
            emoji = emojiFor(AchievementCriteriaType.STARTING_STEPS),
            color = colorFor(AchievementCriteriaType.STARTING_STEPS),
            criteria = AchievementCriteria(AchievementCriteriaType.STARTING_STEPS, threshold = StartingStep.PROFILE_CREATED.mask)
        ),
        Achievement(
            id = 48,
            title = "Currency Explorer",
            description = "Choose a currency from settings",
            emoji = emojiFor(AchievementCriteriaType.STARTING_STEPS),
            color = colorFor(AchievementCriteriaType.STARTING_STEPS),
            criteria = AchievementCriteria(AchievementCriteriaType.STARTING_STEPS, threshold = StartingStep.CURRENCY_SELECTED.mask)
        ),
        Achievement(
            id = 49,
            title = "Make It Yours",
            description = "Change your profile picture",
            emoji = emojiFor(AchievementCriteriaType.STARTING_STEPS),
            color = colorFor(AchievementCriteriaType.STARTING_STEPS),
            criteria = AchievementCriteria(AchievementCriteriaType.STARTING_STEPS, threshold = StartingStep.PROFILE_PICTURE_CHANGED.mask)
        ),
        Achievement(
            id = 50,
            title = "Plant the Seed",
            description = "Create your first asset",
            emoji = emojiFor(AchievementCriteriaType.STARTING_STEPS),
            color = colorFor(AchievementCriteriaType.STARTING_STEPS),
            criteria = AchievementCriteria(AchievementCriteriaType.STARTING_STEPS, threshold = StartingStep.FIRST_ASSET_CREATED.mask)
        ),

        // --- XP Level milestones (up to level 500) ---
        Achievement(
            id = 2, title = "Getting Started", description = "Reach XP level 2",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 2)
        ),
        Achievement(
            id = 3, title = "Novice", description = "Reach XP level 5",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 5)
        ),
        Achievement(
            id = 4, title = "Apprentice", description = "Reach XP level 10",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 10)
        ),
        Achievement(
            id = 5, title = "Skilled", description = "Reach XP level 20",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 20)
        ),
        Achievement(
            id = 6, title = "Proficient", description = "Reach XP level 30",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 30)
        ),
        Achievement(
            id = 7, title = "Expert", description = "Reach XP level 40",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 40)
        ),
        Achievement(
            id = 8, title = "Halfway There", description = "Reach XP level 50",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 50)
        ),
        Achievement(
            id = 9, title = "Veteran", description = "Reach XP level 60",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 60)
        ),
        Achievement(
            id = 10, title = "Elite", description = "Reach XP level 70",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 70)
        ),
        Achievement(
            id = 11, title = "Master", description = "Reach XP level 80",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 80)
        ),
        Achievement(
            id = 12, title = "Grandmaster", description = "Reach XP level 90",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 90)
        ),
        Achievement(
            id = 13, title = "Legend", description = "Reach XP level 100",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 100)
        ),
        Achievement(
            id = 14, title = "Mythic", description = "Reach XP level 250",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 250)
        ),
        Achievement(
            id = 15, title = "Ascended", description = "Reach XP level 500",
            emoji = emojiFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            color = colorFor(AchievementCriteriaType.XP_LEVEL_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.XP_LEVEL_REACHED, threshold = 500)
        ),

        // --- Net worth milestones (up to $1,000,000) ---
        Achievement(
            id = 16, title = "First Grand", description = "Reach a net worth of 1,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 1_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(1_000.0, it)}" }
        ),
        Achievement(
            id = 17, title = "Building Up", description = "Reach a net worth of 3,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 3_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(3_000.0, it)}" }
        ),
        Achievement(
            id = 18, title = "5K Club", description = "Reach a net worth of 5,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 5_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(5_000.0, it)}" }
        ),
        Achievement(
            id = 19, title = "First 10K", description = "Reach a net worth of 10,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 10_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(10_000.0, it)}" }
        ),
        Achievement(
            id = 20, title = "25K Club", description = "Reach a net worth of 25,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 25_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(25_000.0, it)}" }
        ),
        Achievement(
            id = 21, title = "50K Milestone", description = "Reach a net worth of 50,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 50_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(50_000.0, it)}" }
        ),
        Achievement(
            id = 22, title = "75K Milestone", description = "Reach a net worth of 75,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 75_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(75_000.0, it)}" }
        ),
        Achievement(
            id = 23, title = "Six Figures", description = "Reach a net worth of 100,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 100_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(100_000.0, it)}" }
        ),
        Achievement(
            id = 24, title = "Quarter Millionaire", description = "Reach a net worth of 250,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 250_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(250_000.0, it)}" }
        ),
        Achievement(
            id = 25, title = "Half Millionaire", description = "Reach a net worth of 500,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 500_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(500_000.0, it)}" }
        ),
        Achievement(
            id = 26, title = "750K Milestone", description = "Reach a net worth of 750,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 750_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(750_000.0, it)}" }
        ),
        Achievement(
            id = 27, title = "Millionaire", description = "Reach a net worth of 1,000,000",
            emoji = emojiFor(AchievementCriteriaType.NET_WORTH_REACHED),
            color = colorFor(AchievementCriteriaType.NET_WORTH_REACHED),
            criteria = AchievementCriteria(AchievementCriteriaType.NET_WORTH_REACHED, threshold = 1_000_000),
            descriptionFormatter = { "Reach a net worth of ${formatMoney(1_000_000.0, it)}" }
        ),

        // --- Streak milestones (up to 365 days) ---
        Achievement(
            id = 28, title = "Warming Up", description = "Reach a 3 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 3)
        ),
        Achievement(
            id = 29, title = "One Week Strong", description = "Reach a 7 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 7)
        ),
        Achievement(
            id = 30, title = "Two Weeks In", description = "Reach a 14 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 14)
        ),
        Achievement(
            id = 31, title = "One Month Streak", description = "Reach a 30 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 30)
        ),
        Achievement(
            id = 32, title = "Two Months Strong", description = "Reach a 60 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 60)
        ),
        Achievement(
            id = 33, title = "Quarter Year Streak", description = "Reach a 90 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 90)
        ),
        Achievement(
            id = 34, title = "Half Year Streak", description = "Reach a 180 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 180)
        ),
        Achievement(
            id = 35, title = "One Year Streak", description = "Reach a 365 day streak",
            emoji = emojiFor(AchievementCriteriaType.STREAK_DAYS),
            color = colorFor(AchievementCriteriaType.STREAK_DAYS),
            criteria = AchievementCriteria(AchievementCriteriaType.STREAK_DAYS, threshold = 365)
        ),

        // --- Logged events milestones (up to 10,000) ---
        Achievement(
            id = 36, title = "First Event", description = "Log an event",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 1)
        ),
        Achievement(
            id = 37, title = "Beginner Logger", description = "Log 5 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 5)
        ),
        Achievement(
            id = 38, title = "Double Digits", description = "Log 10 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 10)
        ),
        Achievement(
            id = 39, title = "Building a Habit", description = "Log 25 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 25)
        ),
        Achievement(
            id = 40, title = "Regular Logger", description = "Log 50 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 50)
        ),
        Achievement(
            id = 41, title = "Centurion", description = "Log 100 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 100)
        ),
        Achievement(
            id = 42, title = "Dedicated Tracker", description = "Log 250 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 250)
        ),
        Achievement(
            id = 43, title = "Half a Thousand", description = "Log 500 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 500)
        ),
        Achievement(
            id = 44, title = "Thousand Club", description = "Log 1,000 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 1_000)
        ),
        Achievement(
            id = 45, title = "Meticulous", description = "Log 2,500 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 2_500)
        ),
        Achievement(
            id = 46, title = "Data Hoarder", description = "Log 5,000 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 5_000)
        ),
        Achievement(
            id = 47, title = "Logging Legend", description = "Log 10,000 events",
            emoji = emojiFor(AchievementCriteriaType.LOGGED_EVENTS),
            color = colorFor(AchievementCriteriaType.LOGGED_EVENTS),
            criteria = AchievementCriteria(AchievementCriteriaType.LOGGED_EVENTS, threshold = 10_000)
        ),
    )

    // Assigns rank automatically: each achievement's rank is its 1-based
    // position among achievements sharing its criteria type, in roman
    // numerals. Iterating UNRANKED in order (rather than grouping) keeps
    // this correct even if categories were ever interleaved in the list.
    val ALL: List<Achievement> = run {
        val counters = mutableMapOf<AchievementCriteriaType, Int>()
        UNRANKED.map { achievement ->
            val type = achievement.criteria.type
            val nextRank = (counters[type] ?: 0) + 1
            counters[type] = nextRank
            achievement.copy(rank = nextRank, rankRoman = toRoman(nextRank))
        }
    }

    val byId: Map<Int, Achievement> = ALL.associateBy { it.id }
}