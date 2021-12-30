package com.ian.junemon.foodiepedia.feature.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.ian.junemon.foodiepedia.model.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Ian Damping on 26,May,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseViewModel: ViewModel() {

    /**
     * Request a snackbar to display a string.
     *
     * This variable is private because we don't want to expose [MutableLiveData].
     *
     * MutableLiveData allows anyone to set a value, and [PlantListViewModel] is the only
     * class that should be setting values.
     */
    private val _snackbar = MutableLiveData<String?>()

    /**
     * Request a snackbar to display a string.
     */
    val snackbar: LiveData<String?>
        get() = _snackbar

    private val _loadingState = MutableLiveData<Boolean>()
    /**
     * Show a loading spinner if true
     */
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private var baseJob: Job? = null


    protected fun consumeSuspend(func: suspend () -> Unit) {
        baseJob?.cancel()
        baseJob = viewModelScope.launch {
            func.invoke()
        }
    }



    fun setupLoadingState(data:Boolean){
        _loadingState.value = data
    }

    fun setupSnackbarMessage(data:String?){
        _snackbar.value = data
    }

    /**
     * Called immediately after the UI shows the snackbar.
     */
    fun onSnackbarShown() {
        _snackbar.value = null
    }

}