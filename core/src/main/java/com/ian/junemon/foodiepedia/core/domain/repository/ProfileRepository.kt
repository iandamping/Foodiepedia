package com.ian.junemon.foodiepedia.core.domain.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults

interface ProfileRepository {

    fun getUserProfile(): LiveData<ProfileResults<AuthenticatedUserInfo>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout(onComplete: () -> Unit)
}