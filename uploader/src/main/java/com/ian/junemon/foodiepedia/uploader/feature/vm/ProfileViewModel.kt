package com.ian.junemon.foodiepedia.uploader.feature.vm

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.model.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileViewModel @Inject constructor(private val repository: ProfileUseCase) :
    BaseViewModel() {

    private val _moveToUploadFragmentEvent = MutableLiveData<Event<Unit>>()
    val moveToUploadFragmentEvent: LiveData<Event<Unit>> = _moveToUploadFragmentEvent

    fun moveToUploadFragment() {
        _moveToUploadFragmentEvent.value = Event(Unit)
    }

    fun getUserProfile(): LiveData<ProfileResults<AuthenticatedUserInfo>> =
        repository.getUserProfile()

    suspend fun initSignIn(): Intent = repository.initSignIn()

    fun initLogout(successLogout: () -> Unit) {
        viewModelScope.launch {
            repository.initLogout(successLogout)
        }
    }
}