package com.ian.junemon.foodiepedia.uploader.feature.vm

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.uploader.model.Event
import com.ian.junemon.foodiepedia.uploader.feature.state.UserProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileUseCase) :
    BaseViewModel() {

    private val _userData:MutableStateFlow<UserProfileUiState> = MutableStateFlow(UserProfileUiState.initial())
    val userData = _userData.asStateFlow()

    private val _moveToUploadFragmentEvent = MutableLiveData<Event<Unit>>()
    val moveToUploadFragmentEvent: LiveData<Event<Unit>> = _moveToUploadFragmentEvent

    init {
        viewModelScope.launch {
            repository.getUserProfile().collect{ result ->
                when(result) {
                    is RepositoryData.Error -> _userData.update { currentUiState ->
                        currentUiState.copy(errorMessage = result.msg, user = null)
                    }
                    is RepositoryData.Success -> _userData.update { currentUiState ->
                        currentUiState.copy(errorMessage = "", user = result.data)
                    }
                }

            }
        }
    }

    fun moveToUploadFragment() {
        _moveToUploadFragmentEvent.value = Event(Unit)
    }


    suspend fun initSignIn(): Intent = repository.initSignIn()

    fun initLogout(successLogout: () -> Unit) {
        viewModelScope.launch {
            repository.initLogout(successLogout)
        }
    }
}