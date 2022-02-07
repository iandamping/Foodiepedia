package com.ian.junemon.foodiepedia.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Ian Damping on 26,May,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseViewModel : ViewModel() {

    private var baseJob: Job? = null


    protected fun consumeSuspend(func: suspend () -> Unit) {
        baseJob?.cancel()
        baseJob = viewModelScope.launch {
            func.invoke()
        }
    }


}