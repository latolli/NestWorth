package com.example.nestworth.Repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nestworth.Repository.model.Income
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Insert
    suspend fun insertIncome(income: Income)

    @Update
    suspend fun updateIncome(income: Income)

    @Delete
    suspend fun deleteIncome(income: Income)

    @Query("SELECT * FROM incomes ORDER BY date DESC")
    fun getAllIncomes(): Flow<List<Income>>

    @Query("SELECT * FROM incomes WHERE date BETWEEN :startDate AND :endDate")
    fun getIncomesBetweenDates(startDate: Long, endDate: Long): Flow<List<Income>>

    @Query("DELETE FROM incomes")
    suspend fun deleteAllIncomes()
}