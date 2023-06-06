package com.ian.junemon.foodiepedia.core.data.datasource.cache.preference

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by Ian Damping on 30,June,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PreferenceHelperImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : PreferenceHelper {

    override fun saveStringInSharedPreference(key: String, value: String?) {
        editor.putString(key, value).apply()
    }

    override fun getStringInSharedPreference(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun deleteSharedPreference(key: String) {
        editor.remove(key).apply()
    }

    override fun deleteAllSharedPrefrence() {
        editor.clear().apply()
    }

}
