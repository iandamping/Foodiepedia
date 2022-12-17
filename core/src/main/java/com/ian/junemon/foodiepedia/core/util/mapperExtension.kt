package com.ian.junemon.foodiepedia.core.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.Food
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.SavedFood
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.UserProfile
import com.ian.junemon.foodiepedia.core.data.model.FoodEntity
import com.ian.junemon.foodiepedia.core.data.model.UserProfileEntity
import com.ian.junemon.foodiepedia.core.domain.model.*
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by Ian Damping on 03,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun Job?.cancelIfActive() {
    if (this?.isActive == true) {
        cancel()
    }
}

fun DatabaseReference.valueEventFlow(): Flow<PushFirebase> = callbackFlow {

    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot): Unit =
            trySendBlocking(PushFirebase.Changed(snapshot)).getOrThrow()

        override fun onCancelled(error: DatabaseError): Unit =
            trySendBlocking(PushFirebase.Cancelled(error)).getOrThrow()
    }
    addValueEventListener(valueEventListener)
    awaitClose {
        removeEventListener(valueEventListener)
    }
}

suspend fun DatabaseReference.singleValueEvent(): PushFirebase =
    suspendCancellableCoroutine { continuation ->

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                continuation.resume(PushFirebase.Cancelled(error))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(PushFirebase.Changed(snapshot))
            }
        }
        addListenerForSingleValueEvent(valueEventListener) // Subscribe to the event
    }

fun FirebaseAuth.valueEventProfileFlow(): Flow<FirebaseAuth> = channelFlow {
    val profileListener = FirebaseAuth.AuthStateListener {
        channel.trySend(it).isSuccess
    }
    addAuthStateListener(profileListener)

    awaitClose {
        removeAuthStateListener(profileListener)
    }
}


fun FoodCacheDomain.mapToDatabase(): Food =
    Food(
        foodId,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodDescription
    )

fun SavedFood.mapToDetailDatabase(): SavedFoodCacheDomain = SavedFoodCacheDomain(
    localFoodID,
    foodId,
    foodName,
    foodCategory,
    foodArea,
    foodImage,
    foodContributor,
    foodDescription
)

fun SavedFoodCacheDomain.mapToDatabase(): SavedFood =
    SavedFood(
        localFoodID,
        foodId,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodDescription
    )

fun List<FoodCacheDomain>.mapToDatabase(): List<Food> = map { it.mapToDatabase() }

fun List<SavedFood>.mapToDetailDatabase(): List<SavedFoodCacheDomain> =
    map { it.mapToDetailDatabase() }

fun Food.mapToCacheDomain(): FoodCacheDomain = FoodCacheDomain(
    foodId,
    foodName,
    foodCategory,
    foodArea,
    foodImage,
    foodContributor,
    foodDescription
)

fun List<Food>.mapToCacheDomain(): List<FoodCacheDomain> = map { it.mapToCacheDomain() }

fun UserProfileDataModel.mapToDatabase() =
    UserProfile(localID, userID, photoUser, nameUser, emailUser)

fun UserProfileEntity.mapToDomain() =
    UserProfileDataModel(localID, userID, photoUser, nameUser, emailUser)

fun UserProfile.mapToDomain() =
    UserProfileDataModel(localID, userID, photoUser, nameUser, emailUser)

fun Flow<UserProfile>.mapToDomain() = map { it.mapToDomain() }

fun FoodEntity.mapToRemoteDomain(): FoodRemoteDomain =
    FoodRemoteDomain(foodName, foodCategory, foodArea, foodImage, foodContributor, foodDescription)

fun FoodRemoteDomain.mapToCacheDomain(): FoodCacheDomain = FoodCacheDomain(
    null,
    foodName,
    foodCategory,
    foodArea,
    foodImage,
    foodContributor,
    foodDescription
)

fun FoodCachePresentation.mapToDetailDatabasePresentation() = SavedFoodCacheDomain(
    null,
    foodId,
    foodName,
    foodCategory,
    foodArea,
    foodImage,
    foodContributor,
    foodDescription
)

fun FoodCacheDomain.mapToCachePresentation(): FoodCachePresentation =
    FoodCachePresentation(
        foodId,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodDescription
    )

fun List<FoodRemoteDomain>.mapRemoteToCacheDomain(): List<FoodCacheDomain> =
    map { it.mapToCacheDomain() }

fun List<FoodEntity>.mapToRemoteDomain(): List<FoodRemoteDomain> =
    map { it.mapToRemoteDomain() }

fun List<FoodCacheDomain>.mapToCachePresentation(): List<FoodCachePresentation> =
    map { it.mapToCachePresentation() }


fun SavedFoodCacheDomain.mapFavToCachePresentation(): FoodCachePresentation =
    FoodCachePresentation(
        localFoodID,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodDescription
    )

fun List<SavedFoodCacheDomain>.mapListFavToCachePresentation(): List<FoodCachePresentation> =
    map { it.mapFavToCachePresentation() }