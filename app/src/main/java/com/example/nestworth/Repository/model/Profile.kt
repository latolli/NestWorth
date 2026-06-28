package com.example.nestworth.Repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val xpAmount: Int = 0,
    val xpLevel: Int = 0,
    val achievements: List<Int> = emptyList(),
    val creationDate: Long = System.currentTimeMillis()
)
