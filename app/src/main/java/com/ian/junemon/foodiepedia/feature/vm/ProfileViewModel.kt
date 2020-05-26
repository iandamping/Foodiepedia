package com.ian.junemon.foodiepedia.feature.vm

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import com.ian.junemon.foodiepedia.feature.util.Event
import com.junemon.model.ProfileResults
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileViewModel @Inject constructor(private val repository: ProfileUseCase) : ViewModel() {

    private val _moveToUploadFragmentEvent = MutableLiveData<Event<Unit>>()
    val moveToUploadFragmentEvent: LiveData<Event<Unit>> = _moveToUploadFragmentEvent


    fun moveToUploadFragment() {
        _moveToUploadFragmentEvent.value = Event(Unit)
    }

    fun inflateLogin(): LiveData<ProfileResults<UserProfileDataModel>> = repository.getUserProfile()

    fun getUser(): LiveData<UserProfileDataModel> = repository.get()

    suspend fun initSignIn(): Intent = repository.initSignIn()

    fun initLogout(){
        viewModelScope.launch {
            repository.initLogout()
        }
    }
}