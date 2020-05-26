package com.junemon.model

sealed class Results<out R> {
    data class Success<out T>(val data: T) : Results<T>()
    object Loading : Results<Nothing>()
    data class Error<out T>(val exception: Exception,val cache:T?) : Results<T>()
}

sealed class ProfileResults<out R> {
    data class Success<out T>(val data: T) : ProfileResults<T>()
    object Loading : ProfileResults<Nothing>()
    data class Error(val exception: Exception) : ProfileResults<Nothing>()
}


sealed class DataHelper<out T>{
    data class RemoteSourceValue<out T>(val data: T) : DataHelper<T>()
    data class RemoteSourceError(val exception: Exception) : DataHelper<Nothing>()
}


sealed class FirebaseResult<out R> {
    object SuccessPush : FirebaseResult<Nothing>()
    data class ErrorPush(val exception:Exception) : FirebaseResult<Nothing>()
}

sealed class WorkerResult<out R> {
    object Loading : WorkerResult<Nothing>()
    object SuccessWork : WorkerResult<Nothing>()
    data class ErrorWork(val exception:Exception) : WorkerResult<Nothing>()
    object EmptyData : WorkerResult<Nothing>()
}