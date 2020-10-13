package com.ian.junemon.foodiepedia.core.cache.preference

import android.content.SharedPreferences
import com.ian.junemon.foodiepedia.core.cache.preference.PreferenceHelper
import com.ian.junemon.foodiepedia.core.cache.preference.listener.BaseSharedPreferenceListener
import javax.inject.Inject

/**
 * Created by Ian Damping on 23,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PreferenceHelperImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PreferenceHelper {

    private val preHelperEditor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override fun registerListener(listenerClass: BaseSharedPreferenceListener) {
       sharedPreferences.registerOnSharedPreferenceChangeListener(listenerClass)
    }

    override fun unregisterListener(listenerClass: BaseSharedPreferenceListener) {
       sharedPreferences.unregisterOnSharedPreferenceChangeListener(listenerClass)
    }

    override fun saveStringInSharedPreference(key: String?, value: String?) {
        if (value != null){
            preHelperEditor.putString(key, value).apply()
        }
    }

    override fun getStringInSharedPreference(key: String?): String {
        return sharedPreferences.getString(key,"") ?: ""
    }

    override fun saveIntInSharedPreference(key: String?, value: Int?) {
        if (value != null) {
            preHelperEditor.putInt(key, value).apply()
        }
    }

    override fun getIntInSharedPreference(key: String?): Int {
        return sharedPreferences.getInt(key, 0)
    }

    override fun saveBooleanInSharedPreference(key: String, value: Boolean) {
        preHelperEditor.putBoolean(key, value).apply()
    }

    override fun getBooleanInSharedPreference(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun deleteSharedPreference(key: String) {
        preHelperEditor.remove(key).apply()
    }

    override fun deleteAllSharedPrefrence() {
        preHelperEditor.clear().apply()
    }
}