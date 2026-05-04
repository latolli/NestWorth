package com.example.nestworth.Repository.model

import androidx.room.Embedded
import androidx.room.Relation

data class AssetWithDatapoints(
    @Embedded val asset: Asset,
    @Relation(
        parentColumn = "id",
        entityColumn = "assetId"
    )
    val datapoints: List<AssetDatapoint>
)
