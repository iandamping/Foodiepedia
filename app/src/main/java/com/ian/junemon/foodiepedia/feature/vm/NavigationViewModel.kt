package com.ian.junemon.foodiepedia.feature.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Created by Ian Damping on 23,April,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class NavigationViewModel:ViewModel() {

//    private val _setNavigationChannel = Channel<NavDirections>(Channel.CONFLATED)
//    val navigationFlow = _setNavigationChannel.receiveAsFlow()

//    fun setNavigationDirection(directions: NavDirections) {
//        viewModelScope.launch {
//            _setNavigationChannel.send(directions)
//        }
//    }
}