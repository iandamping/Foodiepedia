package com.ian.junemon.foodiepedia.core.domain.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import com.junemon.model.ProfileResults
import com.junemon.model.Results
import com.junemon.model.domain.UserProfileDataModel

interface ProfileRepository {

    fun getUserProfile(): LiveData<ProfileResults<UserProfileDataModel>>

    suspend fun initSignIn(): Intent

    suspend fun initLogout()
}