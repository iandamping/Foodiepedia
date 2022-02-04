package com.ian.junemon.foodiepedia.core.util

import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Created by Ian Damping on 03,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
object DataConstant {
    const val ERROR_EMPTY_DATA = "Empty Data"
    const val RequestReadWrite = 101
    const val RequestSelectGalleryImage = 102
    const val RequestOpenCamera = 234
    const val APPLICATION_ERROR = "Application encounter unknown error"

    const val filterKey = "filter key"
    const val noFilterValue = "Unfiltered"
    const val filterValueBreakfast = "Breakfast"
    const val filterValueDinner = "Dinner"
    const val filterValueLunch = "Lunch"
    const val filterValueSupper = "Supper"
    const val filterValueBrunch = "Brunch"
    const val prefHelperInit = " init preference helper"
    const val DATABASE_NAME = "foodiepedia.db"

    val FILTER_KEY = stringPreferencesKey(filterKey)
    // val BREAKFAST_KEY = preferencesKey<String>(filterValueBreakfast)
    // val LUNCH_KEY = preferencesKey<String>(filterValueLunch)
    // val DINNER_KEY = preferencesKey<String>(filterValueDinner)
    // val SUPPER_KEY = preferencesKey<String>(filterValueSupper)
    // val BRUNCH_KEY = preferencesKey<String>(filterValueBrunch)
}