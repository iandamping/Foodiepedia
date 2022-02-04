package com.ian.junemon.foodiepedia.core.presentation.view

/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface IntentUtilHelper {

    fun intentOpenWebsite(url: String)

    fun intentShareText(text: String)

    fun intentShareImageAndText(tittle: String?, message: String?, imageUrl: String?)
}