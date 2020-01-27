package com.ian.junemon.foodiepedia.core.domain.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import com.junemon.model.domain.UserProfileDataModel

interface ProfileRepository {

    fun inflateLogin(): LiveData<UserProfileDataModel>

    fun get(): LiveData<UserProfileDataModel>

    suspend fun initSignIn(): Intent

    suspend fun initLogout()
}