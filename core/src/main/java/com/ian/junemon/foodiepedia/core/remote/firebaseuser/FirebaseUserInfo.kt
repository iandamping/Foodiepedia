package com.ian.junemon.foodiepedia.core.remote.firebaseuser

import com.google.firebase.auth.FirebaseUser

open class FirebaseUserInfo(
    private val firebaseUser: FirebaseUser?
) : AuthenticatedUserInfo {

    override fun getEmail(): String? = firebaseUser?.email

    override fun getDisplayName(): String? = firebaseUser?.displayName

    override fun getPhotoUrl(): String? = firebaseUser?.photoUrl.toString()
}
