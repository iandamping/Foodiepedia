package com.ian.junemon.foodiepedia.core.domain.usecase

import android.content.Intent
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.junemon.model.domain.UserProfileDataModel
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileUseCase @Inject constructor(private val repo:ProfileRepository) {

    fun inflateLogin(): LiveData<UserProfileDataModel> = repo.inflateLogin()

    fun get(): LiveData<UserProfileDataModel> = repo.get()

    suspend fun initSignIn(): Intent = repo.initSignIn()

    suspend fun initLogout() = repo.initLogout()
}