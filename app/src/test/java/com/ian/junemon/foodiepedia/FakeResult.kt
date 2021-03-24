package com.ian.junemon.foodiepedia

/**
 * Created by Ian Damping on 23,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
sealed class FakeResults<out R> {
    data class Success<out T>(val data: T) : FakeResults<T>()
    object Loading : FakeResults<Nothing>()
    object Complete : FakeResults<Nothing>()
    data class Error(val message: String) : FakeResults<Nothing>()
}