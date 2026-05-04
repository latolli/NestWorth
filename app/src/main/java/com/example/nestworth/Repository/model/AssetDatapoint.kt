package com.example.nestworth.Repository.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "asset_datapoints",
    foreignKeys = [ForeignKey(
        entity = Asset::class,
        parentColumns = ["id"],
        childColumns = ["assetId"],
        onDelete = ForeignKey.CASCADE  // deleting asset deletes its datapoints too
    )],
    indices = [Index("assetId")]
)
data class AssetDatapoint(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assetId: Int,
    val value: Double,
    val liability: Double = 0.0,
    val date: Long = System.currentTimeMillis()
)
