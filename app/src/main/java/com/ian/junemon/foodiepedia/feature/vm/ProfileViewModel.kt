package com.ian.junemon.foodiepedia.feature.vm

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import com.ian.junemon.foodiepedia.feature.event.UserProfileUiState
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
class ProfileViewModel @Inject constructor(private val repository: ProfileUseCase) :
    BaseViewModel() {

    private val _userData: MutableStateFlow<UserProfileUiState> = MutableStateFlow(
        UserProfileUiState.initial())
    val userData = _userData.asStateFlow()


    init {
        consumeSuspend {
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


    suspend fun initSignIn(): Intent = repository.initSignIn()

    suspend fun initLogout(successLogout: () -> Unit) = repository.initLogout(successLogout)
}