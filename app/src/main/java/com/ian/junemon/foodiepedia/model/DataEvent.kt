package com.ian.junemon.foodiepedia.model

/**
 * Created by Ian Damping on 08,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
sealed class DataEvent {
    object PreFetchFoodData : DataEvent()
    object CompletePreFetchFoodData : DataEvent()
    // object ConsumeFilterState : DataEvent()
    // object ConsumeFilterCacheFoodData : DataEvent()
    // object ConsumeUserProfileData : DataEvent()
    // data class Navigate(val navigation: NavDirections) : DataEvent()
}
