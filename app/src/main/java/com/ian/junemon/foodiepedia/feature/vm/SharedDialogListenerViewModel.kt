package com.ian.junemon.foodiepedia.feature.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.presentation.util.Event

/**
 * Created by Ian Damping on 12,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SharedDialogListenerViewModel: ViewModel() {

    private val _setFilterState:MutableLiveData<Event<String>> = MutableLiveData()
    val setFilterState:LiveData<Event<String>> = _setFilterState

    fun setsetFilterState(data:String){
        _setFilterState.value = Event(data)
    }
}