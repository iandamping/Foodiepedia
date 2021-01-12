package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.ian.junemon.foodiepedia.core.dagger.module.IoDispatcher
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.FirebaseUserInfo
import com.junemon.model.DataSourceHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
class ProfileRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val context: Context,
    private val mFirebaseAuth: FirebaseAuth) :
    ProfileRemoteDataSource {

    private var isListening = false

    // Channel that keeps track of User Authentication

    private val channel = ConflatedBroadcastChannel<DataSourceHelper<AuthenticatedUserInfo>>()

    private val listener: ((FirebaseAuth) -> Unit) = { auth ->

        if (!channel.isClosedForSend) {
            channel.offer(DataSourceHelper.DataSourceValue(FirebaseUserInfo(auth.currentUser)))
        } else {
            unregisterListener()
        }
    }

    override fun getUserProfile(): Flow<DataSourceHelper<AuthenticatedUserInfo>> {
        if (!isListening) {
            mFirebaseAuth.addAuthStateListener(listener)
            isListening = true
        }
        return channel.asFlow()
    }
    override suspend fun initSignIn(): Intent {
        return withContext(ioDispatcher) {
            // this is mutable because FirebaseUI requires it to be mutable
            val providers = mutableListOf(
                AuthUI.IdpConfig.GoogleBuilder().setSignInOptions(
                    GoogleSignInOptions.Builder()
                        .requestId()
                        .requestEmail()
                        .build()
                ).build()
            )
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
        }
    }

    override suspend fun initLogout(onComplete: () -> Unit) {
        withContext(ioDispatcher) {
            AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener { onComplete() }
        }
    }

    private fun unregisterListener() {
        mFirebaseAuth.removeAuthStateListener(listener)
    }
}