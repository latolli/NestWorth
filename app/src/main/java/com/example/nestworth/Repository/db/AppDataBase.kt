package com.example.nestworth.Repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nestworth.Repository.dao.AssetDao
import com.example.nestworth.Repository.dao.ExpenseDao
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.Repository.model.Expense


@Database(
    entities = [Expense::class, Asset::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun assetDao(): AssetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nestworth_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}