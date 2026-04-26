package com.example.nestworth

import android.app.Application
import com.example.nestworth.Repository.db.AppDatabase

class NestWorthApp : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}