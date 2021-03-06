package com.ian.junemon.foodiepedia.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

abstract class BaseViewModel : ViewModel() {
    protected val compose = CompositeDisposable()
    val liveDataState: MutableLiveData<BaseState> = MutableLiveData()
    protected fun dispose() {
        if (!compose.isDisposed) compose.dispose()
    }
}