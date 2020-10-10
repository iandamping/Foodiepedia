package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.content.Intent
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.remote.util.ProfileRemoteHelper
import com.junemon.model.DataHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRemoteDataSourceImpl @Inject constructor(private val remoteHelper: ProfileRemoteHelper) :
    ProfileRemoteDataSource {
    override fun getUserProfile(): Flow<DataHelper<AuthenticatedUserInfo>> {
        return remoteHelper.getUserProfile()
    }

    override suspend fun initSignIn(): Intent {
        return remoteHelper.initSignIn()
    }

    override suspend fun initLogout(onComplete: () -> Unit) {
        remoteHelper.initLogout(onComplete)
    }
}