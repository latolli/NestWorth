package com.example.nestworth.Repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nestworth.Repository.dao.AssetDao
import com.example.nestworth.Repository.dao.ExpenseCategoryDao
import com.example.nestworth.Repository.dao.ExpenseDao
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.Repository.model.Expense
import com.example.nestworth.Repository.model.ExpenseCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [Expense::class, Asset::class, ExpenseCategory::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun assetDao(): AssetDao
    abstract fun expenseCategoryDao(): ExpenseCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Callback to prepopulate the database with default expense categories
        private val PREPOPULATE_CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        database.expenseCategoryDao().insertAll(
                            listOf(
                                ExpenseCategory(name = "Food", emoji = "🍕"),
                                ExpenseCategory(name = "Groceries", emoji = "🛒"),
                                ExpenseCategory(name = "Transport", emoji = "🚗"),
                                ExpenseCategory(name = "Housing", emoji = "🏠"),
                                ExpenseCategory(name = "Health", emoji = "💊"),
                                ExpenseCategory(name = "Entertainment", emoji = "🎮"),
                                ExpenseCategory(name = "Shopping", emoji = "🛍️"),
                                ExpenseCategory(name = "Other", emoji = "📦"),
                            )
                        )
                    }
                }
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nestworth_database"
                )
                    .addCallback(PREPOPULATE_CALLBACK)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}