package com.example.nestworth.Repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val value: Double,
    val liability: Double = 0.0,
    val type: String,
    val lastUpdated: Long = System.currentTimeMillis()
)
