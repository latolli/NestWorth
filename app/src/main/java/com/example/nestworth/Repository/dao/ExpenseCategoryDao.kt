package com.example.nestworth.Repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nestworth.Repository.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCategoryDao {
    @Insert
    suspend fun insertExpenseCategory(expenseCategory: ExpenseCategory)

    @Insert
    suspend fun insertAll(categories: List<ExpenseCategory>)

    @Update
    suspend fun updateExpenseCategory(expenseCategory: ExpenseCategory)

    @Delete
    suspend fun deleteExpenseCategory(expenseCategory: ExpenseCategory)

    @Query("SELECT * FROM expense_categories")
    fun getAllExpenseCategories(): Flow<List<ExpenseCategory>>

    @Query("DELETE FROM expense_categories")
    suspend fun deleteAllExpenseCategories()
}