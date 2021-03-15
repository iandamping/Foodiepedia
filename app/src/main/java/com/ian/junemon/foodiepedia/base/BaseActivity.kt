package com.ian.junemon.foodiepedia.base

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by Ian Damping on 15,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseActivity: DaggerAppCompatActivity() {
    private var baseJob: Job? = null

    @Inject
    lateinit var viewHelper: ViewHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewHelper) { fullScreenAnimation() }
    }

    protected fun consumeSuspend(func: suspend () -> Unit) {
        baseJob?.cancel()
        baseJob = lifecycleScope.launchWhenStarted {
            func.invoke()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        baseJob?.cancel()
    }
}