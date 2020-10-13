package com.ian.junemon.foodiepedia.core.cache.preference

import com.ian.junemon.foodiepedia.core.cache.preference.listener.BaseSharedPreferenceListener

interface PreferenceHelper {

    fun registerListener(listenerClass: BaseSharedPreferenceListener)

    fun unregisterListener(listenerClass: BaseSharedPreferenceListener)

    fun saveStringInSharedPreference(key: String?, value: String?)

    fun getStringInSharedPreference(key: String?): String

    fun saveIntInSharedPreference(key: String?, value: Int?)

    fun getIntInSharedPreference(key: String?): Int

    fun saveBooleanInSharedPreference(key: String, value: Boolean)

    fun getBooleanInSharedPreference(key: String): Boolean

    fun deleteSharedPreference(key: String)

    fun deleteAllSharedPrefrence()
}
