package com.example.nestworth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestworth.Repository.db.AppDatabase
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.Repository.model.Expense
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
    fun addAsset(name: String, value: Double, liability: Double, type: String) {
        viewModelScope.launch {
            db.assetDao().insertAsset(
                Asset(name = name, value = value, liability = liability, type = type)
            )
        }
    }

    fun deleteAsset(asset: Asset) {
        viewModelScope.launch {
            db.assetDao().deleteAsset(asset)
        }
    }

    fun updateAssetValue(asset: Asset, newValue: Double, newLiability: Double) {
        viewModelScope.launch {
            db.assetDao().updateAsset(
                asset.copy(value = newValue, liability = newLiability, lastUpdated = System.currentTimeMillis())
            )
        }
    }

    // General
    val allExpenses = db.expenseDao().getAllExpenses()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val totalNetWorth = db.assetDao().getTotalNetWorth()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
}