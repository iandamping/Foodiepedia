package com.ian.junemon.foodiepedia.core.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ian.junemon.foodiepedia.core.cache.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 *
Created by Ian Damping on 05/10/2019.
Github = https://github.com/iandamping
 */
@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_userdata")
    fun loadAll(): Flow<UserProfile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(inputUser: UserProfile)

    @Query("DELETE FROM profile_userdata")
    suspend fun deleteAllData()

    @Transaction
    suspend fun insertAll(inputUser: UserProfile) {
        deleteAllData()
        insertAllUser(inputUser)
    }
}