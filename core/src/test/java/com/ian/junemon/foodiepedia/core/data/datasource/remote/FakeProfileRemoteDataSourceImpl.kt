package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.content.Intent
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileRemoteDataSource
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 06,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeProfileRemoteDataSourceImpl(var fakeProfile: UserProfileDataModel):ProfileRemoteDataSource {

    override suspend fun get(): Flow<UserProfileDataModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun initSignIn(): Intent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun initLogout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}