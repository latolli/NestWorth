package com.example.nestworth.core

data class TutorialContent(val id: Int, val title: String, val body: String)

enum class TutorialEnum(
    val id: Int
)
{
    HOME(0),
    ASSETS(1),
    INCOME_EXPENSES(2)
}

val tutorialPages = listOf(
    TutorialContent(TutorialEnum.HOME.id,
        "Welcome to NestWorth",
        "⭐ Level & XP\n\n" +
                "Earn XP by maintaining login streaks and completing achievements. Gain enough XP to level up and unlock new rewards.\n" +
                "\n\n" +
                "🏠 Your Apartment\n\n" +
                "Your apartment grows with you. Reach higher levels to unlock upgrades and improve your space.\n" +
                "\n\n" +
                "\uD83D\uDCCA Financial Stats\n\n" +
                "Keep an eye on your latest financial stats, including your net wealth, savings rate, and top assets.\n" +
                "\n\n" +
                "\uD83C\uDFC6 Achievements\n\n" +
                "Check your recent achievements to see your progress and discover new goals to work toward.\n" +
                "\n\n" +
                "\uD83D\uDCB0 Income & Expenses\n\n" +
                "Log your income and expenses to keep your finances up to date. These entries determine your savings rate and help you understand how your money is changing."),
    TutorialContent(TutorialEnum.ASSETS.id,
        "Track your assets",
        "💰 Adding assets\n\n" +
                "Add your own assets — savings, investments, property, crypto, anything — along with any loans tied to them. For each one, set its current value and, if it has debt attached (like a car loan or mortgage), its liability too.\n" +
                "\n\n" +
                "📈 Tracking progression\n\n" +
                "As values change, just log a new update for that asset to keep it current. Over time, this builds a history you can view as a chart — see exactly how each asset (and your net worth) has grown or dropped.\n" +
                "\n\n" +
                "🔍 Checking details\n\n" +
                "Tap into any asset for the full picture: its value trend, liability, and complete update history."),
    TutorialContent(TutorialEnum.INCOME_EXPENSES.id,
        "Income/expenses",
        "💸 Logging entries\n\n" +
                "Add income and expenses as often as you like — there's no limit on how frequently you log them. The more consistently you track, the more accurate your savings rate and financial stats will be.\n" +
                "\n\n" +
                "🏷️ Categorizing\n\n" +
                "Sort your expenses into categories to see where your money actually goes. Use the built-in categories or create your own to match your spending habits.\n" +
                "\n\n" +
                "📜 History & edits\n\n" +
                "Every entry you log shows up on the History page, where you can review, edit, or remove it at any time — so a typo or missed detail is never permanent.")
)