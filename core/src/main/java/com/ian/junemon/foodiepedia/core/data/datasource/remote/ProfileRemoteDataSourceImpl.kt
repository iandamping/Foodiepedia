package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.ian.junemon.foodiepedia.core.dagger.qualifier.IoDispatcher
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.FirebaseUserInfo
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.util.valueEventProfileFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val mFirebaseAuth: FirebaseAuth
) :
    ProfileRemoteDataSource {

    override fun getUserProfile(): Flow<DataSourceHelper<AuthenticatedUserInfo>> {
        return mFirebaseAuth.valueEventProfileFlow().map { auth ->
            if (auth.currentUser != null) {
                DataSourceHelper.DataSourceValue(FirebaseUserInfo(auth.currentUser))
            } else {
                DataSourceHelper.DataSourceError(Exception("User not login"))
            }
        }
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

}