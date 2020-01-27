package com.ian.junemon.foodiepedia.core.cache.util.interfaces

import com.ian.junemon.foodiepedia.core.cache.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 02,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ProfileDaoHelper {

    fun loadAll(): Flow<UserProfile>

    suspend fun insertAll(inputUser: UserProfile)

    suspend fun deleteAllData()
}