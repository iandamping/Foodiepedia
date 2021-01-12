package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.content.Intent
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import kotlinx.coroutines.flow.Flow

interface ProfileRemoteDataSource {

    fun getUserProfile(): Flow<DataSourceHelper<AuthenticatedUserInfo>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout(onComplete: () -> Unit)
}