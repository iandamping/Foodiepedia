package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.ian.junemon.foodiepedia.core.dagger.module.DefaultDispatcher
import com.ian.junemon.foodiepedia.core.util.DataConstant
import com.junemon.model.DataSourceHelper
import com.junemon.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.data.model.data.FoodEntity
import com.ian.junemon.foodiepedia.core.data.model.data.dto.mapToRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodRemoteDomain
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
class FoodRemoteDataSourceImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val storagePlaceReference: StorageReference,
    private val databasePlaceReference: DatabaseReference
) : FoodRemoteDataSource {


    override suspend fun getFirebaseData(): Flow<DataSourceHelper<List<FoodRemoteDomain>>> =
        callbackFlow {
            val container: MutableSet<FoodEntity> = mutableSetOf()
            val databaseListener = object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.map {
                        it.getValue(FoodEntity::class.java)
                    }.forEach {
                        if (it != null) {
                            container.add(it)
                        }
                    }
                    if (container.isNullOrEmpty()) {
                        sendBlocking(DataSourceHelper.DataSourceError(Exception(DataConstant.ERROR_EMPTY_DATA)))
                    } else {
                        sendBlocking(
                            DataSourceHelper.DataSourceValue(
                                container.toList().mapToRemoteDomain()
                            )
                        )
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    sendBlocking(DataSourceHelper.DataSourceError(p0.toException()))
                }
            }
            databasePlaceReference.addValueEventListener(databaseListener)

            awaitClose {
                databasePlaceReference
                    .removeEventListener(databaseListener)
            }

        }

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
                /**This is where we put file into firebase storage*/
                reference.putFile(imageUri).run {
                    /**we try to check if it success or failed to upload file in firebase storage*/
                    /**we try to check if it success or failed to upload file in firebase storage*/
                    addOnSuccessListener {
                        /** now we try to donwload the url for image that has been put into firebase storage*/
                        /** now we try to donwload the url for image that has been put into firebase storage*/
                        reference.downloadUrl.run {
                            /**we try to check if it success or failed to download url from firebase storage*/
                            /**we try to check if it success or failed to download url from firebase storage*/
                            addOnSuccessListener {
                                /** if we succed now we use that url to put it into our model,
                                 * and upload it into our firebase database*/
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