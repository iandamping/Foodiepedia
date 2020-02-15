package com.ian.junemon.foodiepedia.core.cache.util

import android.app.Application
import android.content.Context

class PreferenceHelper(app: Application) {
    private val prefHelperInit = " init preference helper"

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

    fun saveIntInSharedPreference(key: String?, value: Int?) {
        if (value != null) {
            preHelperEditor.putInt(key, value).apply()
        }
    }

    fun getIntInSharedPreference(key: String?): Int? {
        return prefHelp.getInt(key, 0)
    }
}