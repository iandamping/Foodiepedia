package com.ian.junemon.foodiepedia.core.remote.util

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.ian.junemon.foodiepedia.core.data.di.DefaultDispatcher
import com.ian.junemon.foodiepedia.core.data.di.IoDispatcher
import com.junemon.model.DataHelper
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
class ProfileRemoteHelperImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val mFirebaseAuth: FirebaseAuth
) : ProfileRemoteHelper {
    private var isListening = false
    private val channel = ConflatedBroadcastChannel<DataHelper<UserProfileDataModel>>()

    private val listeners: FirebaseAuth.AuthStateListener by lazy {
        FirebaseAuth.AuthStateListener {
            try {
                if (!channel.isClosedForSend) {
                    checkNotNull(mFirebaseAuth.currentUser)
                    channel.offer(
                        DataHelper.RemoteSourceValue(
                            UserProfileDataModel(
                                null,
                                mFirebaseAuth.currentUser?.uid,
                                mFirebaseAuth.currentUser?.photoUrl.toString(),
                                mFirebaseAuth.currentUser?.displayName,
                                mFirebaseAuth.currentUser?.email
                            )
                        )
                    )
                } else {
                    unregisterListener()
                }
            } catch (e: Exception) {
                channel.offer(
                    DataHelper.RemoteSourceError(e)
                )
            }
        }
    }

    private fun registerListener() {
        mFirebaseAuth.addAuthStateListener(listeners)
    }

    private fun unregisterListener() {
        mFirebaseAuth.removeAuthStateListener(listeners)
    }

    override suspend fun getUserProfile(): Flow<DataHelper<UserProfileDataModel>> {
        if (!isListening) {
            registerListener()
            isListening = true
        }
        return channel.asFlow().flowOn(defaultDispatcher).conflate()
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

    override suspend fun initLogout() {
        mFirebaseAuth.signOut()
    }
}