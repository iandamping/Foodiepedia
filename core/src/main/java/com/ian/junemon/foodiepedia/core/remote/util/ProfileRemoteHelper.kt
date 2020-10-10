package com.ian.junemon.foodiepedia.core.remote.util

import android.content.Intent
import com.ian.junemon.foodiepedia.core.remote.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.DataHelper
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ProfileRemoteHelper {

    fun getUserProfile(): Flow<DataHelper<AuthenticatedUserInfo>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout(onComplete: () -> Unit)
}