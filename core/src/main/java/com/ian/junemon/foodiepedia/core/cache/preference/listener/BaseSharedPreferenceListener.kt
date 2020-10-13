package com.ian.junemon.foodiepedia.core.cache.preference.listener

import android.content.SharedPreferences

/**
 * Created by Ian Damping on 23,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseSharedPreferenceListener : SharedPreferences.OnSharedPreferenceChangeListener{
    private var _key: String = ""

    protected fun setKey(key: String) {
        _key = key
    }

    protected fun resetKey(){
        _key = ""
    }

    protected fun getKey() = _key
}