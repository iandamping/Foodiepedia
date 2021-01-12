package com.ian.junemon.foodiepedia.core.domain.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.ProfileResults
import com.ian.junemon.foodiepedia.core.domain.model.domain.UserProfileDataModel

interface ProfileRepository {

    fun getUserProfile(): LiveData<ProfileResults<AuthenticatedUserInfo>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout(onComplete: () -> Unit)
}