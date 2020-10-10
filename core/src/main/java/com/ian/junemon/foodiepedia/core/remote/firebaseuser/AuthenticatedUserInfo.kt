package com.ian.junemon.foodiepedia.core.remote.firebaseuser


interface AuthenticatedUserInfo {

    fun getEmail(): String?

    fun getDisplayName(): String?

    fun getPhotoUrl(): String?

}