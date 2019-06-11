package com.example.junemon.foodiepedia.util

import android.app.Application
import android.content.Context
import com.example.junemon.foodiepedia.util.Constant.prefHelperInit

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */
class PreferenceHelper(app: Application) {
    private val prefHelp by lazy {
        app.getSharedPreferences(prefHelperInit, Context.MODE_PRIVATE)
    }
    private val preHelperEditor = prefHelp.edit()

    fun saveStringInSharedPreference(key: String?, value: String?) {
        preHelperEditor.putString(key, value).apply()
    }

    fun getStringInSharedPreference(key: String?): String? {
        return prefHelp.getString(key, "")
    }
}