package com.example.junemon.foodapi_mvvm.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
Created by Ian Damping on 23/04/2019.
Github = https://github.com/iandamping
 */
class GenericViewModel<T> : ViewModel() {
    private var genericData: MutableLiveData<T> = MutableLiveData()

    fun setGenericViewModelData(data: T) {
        this.genericData.value = data
    }

    fun getGenericViewModelData(): MutableLiveData<T> = this.genericData
}