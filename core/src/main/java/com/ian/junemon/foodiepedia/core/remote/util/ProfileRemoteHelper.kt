package com.ian.junemon.foodiepedia.core.remote.util

import android.content.Intent
import com.junemon.model.DataHelper
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ProfileRemoteHelper {

    suspend fun getUserProfile(): Flow<DataHelper<UserProfileDataModel>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout()
}