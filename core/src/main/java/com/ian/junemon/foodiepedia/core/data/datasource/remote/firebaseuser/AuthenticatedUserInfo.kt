package com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser


interface AuthenticatedUserInfo {

    fun getEmail(): String?

    fun getDisplayName(): String?

    fun getPhotoUrl(): String?

    fun getUid():String?

}