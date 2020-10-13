package com.ian.junemon.foodiepedia.core.cache.preference.listener

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Ian Damping on 24,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class IntPrefValueListener: BaseSharedPreferenceListener() {

    private val _intPreferenceValue: MutableStateFlow<Int?> = MutableStateFlow(null)

    val intPreferenceValue: StateFlow<Int?> = _intPreferenceValue

    fun setListenKey(key: String) {
        setKey(key)
    }

    fun resetListenKey() {
        resetKey()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        sharedPreferences?.let {
            if (getKey() != "") {
                if (key.equals(getKey())) {
                    _intPreferenceValue.value = it.getInt(getKey(),0)
                }
            }
        }
    }
}