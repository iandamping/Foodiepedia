package com.ian.junemon.foodiepedia.uploader.feature.state

import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo


/**
 * Created by Ian Damping on 30,December,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class UserProfileUiState(
    val errorMessage: String,
    val user: AuthenticatedUserInfo?
) {
    companion object {
        fun initial() = UserProfileUiState(
            errorMessage = "",
            user = null
        )
    }
}
