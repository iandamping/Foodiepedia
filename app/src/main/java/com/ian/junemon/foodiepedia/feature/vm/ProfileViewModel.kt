package com.ian.junemon.foodiepedia.feature.vm

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileViewModel @Inject constructor(private val repository: ProfileUseCase) :
    BaseViewModel() {

    fun getUserProfile(): LiveData<ProfileResults<AuthenticatedUserInfo>> =
        repository.getUserProfile().asLiveData()

    suspend fun initSignIn(): Intent = repository.initSignIn()

    suspend fun initLogout(successLogout: () -> Unit) = repository.initLogout(successLogout)
}