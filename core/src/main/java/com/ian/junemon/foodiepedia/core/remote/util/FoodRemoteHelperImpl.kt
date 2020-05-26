package com.ian.junemon.foodiepedia.core.remote.util

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.ian.junemon.foodiepedia.core.data.di.DefaultDispatcher
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.data.FoodEntity
import com.junemon.model.data.dto.mapToRemoteDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodRemoteHelperImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val storagePlaceReference: StorageReference,
    private val databasePlaceReference: DatabaseReference
) : FoodRemoteHelper {

    @ExperimentalCoroutinesApi
    override suspend fun getFirebaseData(): DataHelper<List<FoodRemoteDomain>> {
        val result: CompletableDeferred<DataHelper<List<FoodRemoteDomain>>> = CompletableDeferred()
        withContext(defaultDispatcher) {
            val container: MutableSet<FoodEntity> = mutableSetOf()
            databasePlaceReference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    result.complete(DataHelper.RemoteSourceError(p0.toException()))
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        container.add(it.getValue(FoodEntity::class.java)!!)
                    }
                    result.complete(
                        DataHelper.RemoteSourceValue(
                            container.toList().mapToRemoteDomain()
                        )
                    )
                }
            })
        }
        return result.await()
    }

    @ExperimentalCoroutinesApi
    override suspend fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): FirebaseResult<Nothing> {
        val result: CompletableDeferred<FirebaseResult<Nothing>> = CompletableDeferred()
        withContext(defaultDispatcher) {
            try {
                checkNotNull(imageUri.lastPathSegment) { "last path segment from imageuri is null" }
                val reference = storagePlaceReference.child(imageUri.lastPathSegment!!)
                /**This is where we put file into firebase storage*/
                reference.putFile(imageUri).run {
                    /**we try to check if it success or failed to upload file in firebase storage*/
                    addOnSuccessListener {
                        /** now we try to donwload the url for image that has been put into firebase storage*/
                        reference.downloadUrl.run {
                            /**we try to check if it success or failed to download url from firebase storage*/
                            addOnSuccessListener {
                                /** if we succed now we use that url to put it into our model,
                                 * and upload it into our firebase database*/
                                data.foodImage = it.toString()
                                databasePlaceReference.push().setValue(data)
                                    .addOnFailureListener { exceptions ->
                                        result.complete(FirebaseResult.ErrorPush(exceptions))
                                    }.addOnSuccessListener {
                                        result.complete(FirebaseResult.SuccessPush)
                                    }
                            }

                            addOnFailureListener {
                                result.complete(FirebaseResult.ErrorPush(it))
                            }
                        }
                    }
                    addOnFailureListener {
                        result.complete(FirebaseResult.ErrorPush(it))
                    }
                }
            } catch (e: Exception) {
                result.complete(FirebaseResult.ErrorPush(e))
            }
        }
        return result.await()
    }
}