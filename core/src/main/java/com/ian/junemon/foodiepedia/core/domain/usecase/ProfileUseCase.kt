package com.ian.junemon.foodiepedia.core.domain.usecase

import android.content.Intent
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 03,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ProfileUseCase {

    fun getUserProfile(): Flow<RepositoryData<AuthenticatedUserInfo>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout(onComplete: () -> Unit)
}