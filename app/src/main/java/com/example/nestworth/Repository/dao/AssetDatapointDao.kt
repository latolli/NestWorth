package com.example.nestworth.Repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nestworth.Repository.model.AssetDatapoint
import com.example.nestworth.Repository.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDatapointDao {
    @Insert
    suspend fun insertDatapoint(datapoint: AssetDatapoint)

    @Delete
    suspend fun deleteDatapoint(datapoint: AssetDatapoint)

    @Update
    suspend fun updateDatapoint(datapoint: AssetDatapoint)

    @Query("SELECT * FROM asset_datapoints WHERE assetId = :assetId ORDER BY date DESC")
    fun getDatapointsForAsset(assetId: Int): Flow<List<AssetDatapoint>>

    // Most recent datapoint per asset, useful for current net worth
    @Query("""
        SELECT * FROM asset_datapoints 
        WHERE id IN (
            SELECT id FROM asset_datapoints 
            GROUP BY assetId 
            HAVING date = MAX(date)
        )
    """)
    fun getLatestDatapoints(): Flow<List<AssetDatapoint>>

    @Query("DELETE FROM asset_datapoints")
    suspend fun deleteAllDatapoints()
}