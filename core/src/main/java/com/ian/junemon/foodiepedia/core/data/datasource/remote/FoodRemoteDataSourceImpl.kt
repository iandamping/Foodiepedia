package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.ian.junemon.foodiepedia.core.dagger.qualifier.DefaultDispatcher
import com.ian.junemon.foodiepedia.core.dagger.qualifier.IoDispatcher
import com.ian.junemon.foodiepedia.core.data.model.FoodEntity
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.PushFirebase
import com.ian.junemon.foodiepedia.core.util.mapToRemoteDomain
import com.ian.junemon.foodiepedia.core.util.valueEventFlow
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storagePlaceReference: StorageReference,
    private val databasePlaceReference: DatabaseReference
) : FoodRemoteDataSource {

    override fun getFirebaseData(): Flow<DataSourceHelper<List<FoodRemoteDomain>>> =
        databasePlaceReference.valueEventFlow().map { value ->
            when (value) {
                is PushFirebase.Changed -> {
                    val result = value.snapshot.children.mapNotNull {
                        it.getValue(FoodEntity::class.java)
                    }.toList()
                    DataSourceHelper.DataSourceValue(result.mapToRemoteDomain())
                }
                is PushFirebase.Cancelled -> {
                    DataSourceHelper.DataSourceError(value.error.toException())
                }
            }
        }.catch { DataSourceHelper.DataSourceError(Exception(it)) }
            .flowOn(ioDispatcher)

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