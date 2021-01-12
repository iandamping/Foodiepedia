package com.ian.junemon.foodiepedia.util.interfaces

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface IntentUtilHelper {

    fun intentOpenWebsite(activity: Activity, url: String)

    fun intentShareText(activity: FragmentActivity, text: String)

    suspend fun intentShareImageAndText(tittle: String?, message: String?, imageUrl: String?,intent:(Intent)->Unit)
}