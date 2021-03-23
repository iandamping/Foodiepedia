package com.ian.junemon.foodiepedia.core.domain.model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

sealed class Results<out R> {
    data class Success<out T>(val data: T) : Results<T>()
    object Loading : Results<Nothing>()
    data class Error(val exception: Exception) : Results<Nothing>()
}

sealed class ProfileResults<out R> {
    data class Success<out T>(val data: T) : ProfileResults<T>()
    data class Error(val exception: Exception) : ProfileResults<Nothing>()
}


sealed class DataSourceHelper<out T>{
    data class DataSourceValue<out T>(val data: T) : DataSourceHelper<T>()
    data class DataSourceError(val exception: Exception) : DataSourceHelper<Nothing>()
}


sealed class FirebaseResult<out R> {
    object SuccessPush : FirebaseResult<Nothing>()
    data class ErrorPush(val exception:Exception) : FirebaseResult<Nothing>()
}

sealed class Prefetch{
    object SuccessPrefetch:Prefetch()
    data class FailedPrefetch(val exception: Exception) : Prefetch()
}

sealed class PushFirebase {
    data class Changed(val snapshot: DataSnapshot): PushFirebase()
    data class Cancelled(val error: DatabaseError): PushFirebase()
}
