package com.ian.junemon.foodiepedia.core.domain.model

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

