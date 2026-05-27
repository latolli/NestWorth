package com.example.nestworth.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestworth.Repository.db.AppDatabase
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.Repository.model.AssetDatapoint
import com.example.nestworth.Repository.model.AssetWithDatapoints
import com.example.nestworth.Repository.model.Expense
import com.example.nestworth.Repository.model.ExpenseCategory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

    fun updateExpense(expense: Expense, amount: Double, category: String, note: String) {
        viewModelScope.launch {
            db.expenseDao().updateExpense(
                expense.copy(amount = amount, category = category, note = note)
            )
        }
    }

    // Assets
    fun addAssetWithDatapoint(name: String, type: String, value: Double, liability: Double) {
        viewModelScope.launch {
            val assetId = db.assetDao().insertAsset(
                Asset(name = name, type = type)
            )
            db.assetDatapointDao().insertDatapoint(
                AssetDatapoint(assetId = assetId.toInt(), value = value, liability = liability)
            )
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
        .map { list ->
            list.sumOf { assetWithDatapoints ->
                val latest = assetWithDatapoints.datapoints.maxByOrNull { it.date }
                (latest?.value ?: 0.0) - (latest?.liability ?: 0.0)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

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
    @RequiresApi(Build.VERSION_CODES.O)
    fun addDatapoint(asset: Asset, value: Double, liability: Double, date: LocalDate) {
        viewModelScope.launch {
            val epochMillis = date
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
            db.assetDatapointDao().insertDatapoint(
                AssetDatapoint(assetId = asset.id, value = value, liability = liability, date = epochMillis)
            )
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

    // General
    val allExpenses = db.expenseDao().getAllExpenses()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}