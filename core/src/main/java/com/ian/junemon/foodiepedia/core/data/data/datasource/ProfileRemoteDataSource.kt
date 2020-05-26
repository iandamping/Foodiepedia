package com.ian.junemon.foodiepedia.core.data.data.datasource

import android.content.Intent
import com.junemon.model.DataHelper
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.Flow

interface ProfileCacheDataSource {

    suspend fun setCache(data: UserProfileDataModel)

    fun getCache(): Flow<UserProfileDataModel>

    suspend fun deleteCache()
}

interface ProfileRemoteDataSource {

    suspend fun getUserProfile(): Flow<DataHelper<UserProfileDataModel>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout()
}