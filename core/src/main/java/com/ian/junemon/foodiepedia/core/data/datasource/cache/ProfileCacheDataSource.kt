package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.domain.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.Flow

interface ProfileCacheDataSource {

    suspend fun setCache(data: UserProfileDataModel)

    fun getCache(): Flow<UserProfileDataModel>

    suspend fun deleteCache()
}
