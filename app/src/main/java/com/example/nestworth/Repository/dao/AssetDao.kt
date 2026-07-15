package com.example.nestworth.Repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.Repository.model.AssetWithDatapoints
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Insert
    suspend fun insertAsset(asset: Asset): Long

    @Update
    suspend fun updateAsset(asset: Asset)

    @Delete
    suspend fun deleteAsset(asset: Asset)

    @Transaction
    @Query("SELECT * FROM assets")
    fun getAllAssetsWithDatapoints(): Flow<List<AssetWithDatapoints>>

    @Query("SELECT * FROM assets")
    fun getAllAssets(): Flow<List<Asset>>

    @Query("DELETE FROM assets")
    suspend fun deleteAllAssets()

    /*
    @Query("SELECT SUM(value) FROM assets")
    fun getTotalAssetValue(): Flow<Double>

    @Query("SELECT SUM(liability) FROM assets")
    fun getTotalAssetLiability(): Flow<Double>

    @Query("SELECT SUM(value - liability) FROM assets")
    fun getTotalNetWorth(): Flow<Double>
    */
}