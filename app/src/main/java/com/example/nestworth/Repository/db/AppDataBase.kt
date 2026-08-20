package com.example.nestworth.Repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nestworth.Repository.dao.ProfileDao
import com.example.nestworth.Repository.dao.AssetDao
import com.example.nestworth.Repository.dao.AssetDatapointDao
import com.example.nestworth.Repository.dao.ExpenseCategoryDao
import com.example.nestworth.Repository.dao.ExpenseDao
import com.example.nestworth.Repository.model.Profile
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.Repository.model.AssetDatapoint
import com.example.nestworth.Repository.model.Expense
import com.example.nestworth.Repository.model.ExpenseCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [Expense::class,
            Asset::class,
            ExpenseCategory::class,
            AssetDatapoint::class,
            Profile::class],
    version = 18,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun assetDao(): AssetDao
    abstract fun expenseCategoryDao(): ExpenseCategoryDao
    abstract fun assetDatapointDao(): AssetDatapointDao
    abstract fun profileDao(): ProfileDao

    @Transaction
    suspend fun clearAllUserData() {
        assetDatapointDao().deleteAllDatapoints()
        assetDao().deleteAllAssets()
        expenseDao().deleteAllExpenses()
        expenseCategoryDao().deleteAllExpenseCategories()
    }


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nestworth_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}