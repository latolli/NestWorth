package com.example.nestworth.Repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nestworth.Repository.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert
    suspend fun insertProfile(profile: Profile): Long

    @Update
    suspend fun updateProfile(profile: Profile)

    @Delete
    suspend fun deleteProfile(profile: Profile)

    @Query("SELECT * FROM profiles ORDER BY creationDate DESC")
    fun getAllProfiles(): Flow<List<Profile>>
}