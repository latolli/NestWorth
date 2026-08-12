package com.example.nestworth.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestworth.Repository.db.AppDatabase
import com.example.nestworth.Repository.model.Profile
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.Repository.model.AssetDatapoint
import com.example.nestworth.Repository.model.AssetWithDatapoints
import com.example.nestworth.Repository.model.Expense
import com.example.nestworth.Repository.model.ExpenseCategory
import com.example.nestworth.achievement.AchievementEvaluator
import com.example.nestworth.core.Constants.XP_PER_LEVEL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

class MainViewModel(private val db: AppDatabase) : ViewModel() {

    // Expenses
    fun addExpense(amount: Double, category: String, note: String) {
        viewModelScope.launch {
            db.expenseDao().insertExpense(
                Expense(amount = amount, category = category, note = note)
            )
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            db.expenseDao().deleteExpense(expense)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateExpense(expense: Expense, amount: Double, category: String, note: String, date: LocalDate) {
        viewModelScope.launch {
            val epochMillis = date
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
            db.expenseDao().updateExpense(
                expense.copy(amount = amount, category = category, note = note, date = epochMillis)
            )
        }
    }

    val allExpenses = db.expenseDao().getAllExpenses()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Assets
    // Now takes the profile directly and checks achievements *after* the write
    // has actually committed and a fresh net worth has been computed, instead of
    // leaving the caller to read totalNetWorth.value immediately afterward
    // (which can still be stale due to StateFlow propagation lag).
    fun addAssetWithDatapoint(profile: Profile, name: String, type: String, value: Double, liability: Double) {
        viewModelScope.launch {
            val assetId = db.assetDao().insertAsset(
                Asset(name = name, type = type)
            )
            db.assetDatapointDao().insertDatapoint(
                AssetDatapoint(assetId = assetId.toInt(), value = value, liability = liability)
            )
            val freshNetWorth = computeNetWorth()
            checkAchievements(profile, freshNetWorth)
        }
    }

    fun deleteAsset(asset: Asset) {
        viewModelScope.launch {
            db.assetDao().deleteAsset(asset)
        }
    }

    val allAssets = db.assetDao().getAllAssets()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val allAssetsWithDatapoints = db.assetDao().getAllAssetsWithDatapoints()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Total net worth derived from latest datapoint of each asset
    val totalNetWorth: StateFlow<Double> = allAssetsWithDatapoints
        .map { list -> computeNetWorth(list) }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    // Reads the current asset list straight from the DB flow (bypassing the
    // StateFlow cache) and computes net worth from it. Safe to call right after
    // a suspend insert completes, since Room's invalidation has already fired
    // by the time the insert's coroutine resumes.
    private suspend fun computeNetWorth(): Double =
        computeNetWorth(db.assetDao().getAllAssetsWithDatapoints().first())

    private fun computeNetWorth(list: List<AssetWithDatapoints>): Double =
        list.sumOf { assetWithDatapoints ->
            val latest = assetWithDatapoints.datapoints.maxByOrNull { it.date }
            (latest?.value ?: 0.0) - (latest?.liability ?: 0.0)
        }

    val netWorthGrowthLast30Days: StateFlow<Double> = allAssetsWithDatapoints
        .map { list ->
            val now = System.currentTimeMillis()
            val thirtyDaysAgo = now - (30L * 24 * 60 * 60 * 1000)

            val currentNW = list.sumOf { asset ->
                val latest = asset.datapoints.maxByOrNull { it.date }
                (latest?.value ?: 0.0) - (latest?.liability ?: 0.0)
            }

            val pastNW = list.sumOf { asset ->
                // Most recent datapoint that existed 30 days ago
                val pastLatest = asset.datapoints
                    .filter { it.date <= thirtyDaysAgo }
                    .maxByOrNull { it.date }
                (pastLatest?.value ?: 0.0) - (pastLatest?.liability ?: 0.0)
            }

            currentNW - pastNW
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    val netWorthGrowthPercent: StateFlow<Double?> = combine(
        totalNetWorth,
        netWorthGrowthLast30Days
    ) { current, growth ->
        val past = current - growth
        if (past != 0.0) (growth / past) * 100 else null
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val highestEquityAsset: StateFlow<Pair<String, Double>?> = allAssetsWithDatapoints
        .map { assets ->
            assets
                .mapNotNull { asset ->
                    asset.datapoints
                        .maxByOrNull { it.date }
                        ?.let { latest -> asset.asset.name to (latest.value - latest.liability) }
                }
                .maxByOrNull { it.second }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val totalAssetValue: StateFlow<Double> = allAssetsWithDatapoints
        .map { list ->
            list.sumOf { assetWithDatapoints ->
                assetWithDatapoints.datapoints.maxByOrNull { it.date }?.value ?: 0.0
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    val totalLiability: StateFlow<Double> = allAssetsWithDatapoints
        .map { list ->
            list.sumOf { assetWithDatapoints ->
                assetWithDatapoints.datapoints.maxByOrNull { it.date }?.liability ?: 0.0
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    // Asset datapoints
    // Also updated to take the profile and check achievements after the write commits.
    @RequiresApi(Build.VERSION_CODES.O)
    fun addDatapoint(profile: Profile, asset: Asset, value: Double, liability: Double, date: LocalDate) {
        viewModelScope.launch {
            val epochMillis = date
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
            db.assetDatapointDao().insertDatapoint(
                AssetDatapoint(assetId = asset.id, value = value, liability = liability, date = epochMillis)
            )
            val freshNetWorth = computeNetWorth()
            checkAchievements(profile, freshNetWorth)
        }
    }

    fun deleteDatapoint(datapoint: AssetDatapoint) {
        viewModelScope.launch {
            db.assetDatapointDao().deleteDatapoint(datapoint)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDatapoint(datapoint: AssetDatapoint, value: Double, liability: Double, date: LocalDate) {
        viewModelScope.launch {
            val epochMillis = date
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
            db.assetDatapointDao().updateDatapoint(
                datapoint.copy(value = value, liability = liability, date = epochMillis)
            )
        }
    }

    fun getDatapointsForAsset(assetId: Int): StateFlow<List<AssetDatapoint>> =
        db.assetDatapointDao().getDatapointsForAsset(assetId)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getAssetWithDatapoints(assetId: Int): StateFlow<AssetWithDatapoints?> =
        allAssetsWithDatapoints
            .map { list -> list.find { it.asset.id == assetId } }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    // Expense Categories
    fun addExpenseCategory(name: String, emoji: String) {
        viewModelScope.launch {
            db.expenseCategoryDao().insertExpenseCategory(
                ExpenseCategory(name = name, emoji = emoji)
            )
        }
    }

    val allExpenseCategories = db.expenseCategoryDao().getAllExpenseCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Profile
    fun addProfile(name: String) {
        viewModelScope.launch {
            db.profileDao().insertProfile(
                Profile(name = name, xpAmount = 0, xpLevel = 0, achievements = List(1) { 1 }) // Auto unlock first achievement
            )
        }
    }

    // Delete profile and ALL user data
    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            db.profileDao().deleteProfile(profile)
            db.clearAllUserData()
        }
    }

    fun updateProfile(profile: Profile, name: String, xpAmount: Int, xpLevel: Int,
                      achievements: List<Int>, streak: Int, lastLogin: Long){
        viewModelScope.launch {
            // Check level up
            // Never reset XP amount, just display it correctly
            val newLevel = xpAmount / XP_PER_LEVEL
            val updatedProfile = profile.copy(name = name, xpAmount = xpAmount, xpLevel = newLevel,
                achievements = achievements, dailyStreak = streak, lastLogin = lastLogin)
            db.profileDao().updateProfile(updatedProfile)
            if ((newLevel > profile.xpLevel) || (streak != profile.dailyStreak)) {
                val freshNetWorth = computeNetWorth()
                checkAchievements(updatedProfile, freshNetWorth)
            }
        }
    }

    val allProfiles: StateFlow<List<Profile>?> = db.profileDao().getAllProfiles()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    // For now, support only 1 profile and always use latest from database
    val latestProfile: StateFlow<Profile?> = allProfiles
        .map { profiles ->
            profiles?.maxByOrNull { it.creationDate }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    // Achievements
    private val _achievementUnlockedEvent = MutableStateFlow<List<Int>?>(null)
    val achievementUnlockedEvent: StateFlow<List<Int>?> = _achievementUnlockedEvent

    fun checkAchievements(profile: Profile, netWorth: Double) {
        val result = AchievementEvaluator.evaluateAchievements(profile, netWorth)
        if (result.newIds.isNotEmpty()) {
            updateProfile(profile, profile.name, result.updatedProfile.xpAmount, result.updatedProfile.xpLevel,
                result.updatedProfile.achievements, profile.dailyStreak, profile.lastLogin)
            _achievementUnlockedEvent.value = (_achievementUnlockedEvent.value ?: emptyList()) + result.newIds
        }
    }

    fun consumeAchievementEvent() {
        _achievementUnlockedEvent.value = null
    }
}