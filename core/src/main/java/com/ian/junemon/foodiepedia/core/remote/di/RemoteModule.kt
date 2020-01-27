package com.ian.junemon.foodiepedia.core.remote.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ian.junemon.foodiepedia.core.BuildConfig.firebaseStorageUrl
import com.ian.junemon.foodiepedia.core.BuildConfig.foodNode
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object RemoteModule {

    @Provides
    @JvmStatic
    fun provideFirebaseDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference.child(foodNode)
    }

    @Provides
    @JvmStatic
    fun provideFirebaseStorage(): StorageReference {
        return FirebaseStorage.getInstance().getReferenceFromUrl(firebaseStorageUrl)
    }

    @Provides
    @JvmStatic
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}