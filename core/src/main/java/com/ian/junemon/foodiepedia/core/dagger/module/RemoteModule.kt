package com.ian.junemon.foodiepedia.core.dagger.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.core.BuildConfig.firebaseStorageUrl
import com.ian.junemon.foodiepedia.core.BuildConfig.foodNode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideFirebaseDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child(foodNode)
    }

    @Provides
    fun provideFirebaseStorage(): StorageReference {
        return FirebaseStorage.getInstance().getReferenceFromUrl(firebaseStorageUrl)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}