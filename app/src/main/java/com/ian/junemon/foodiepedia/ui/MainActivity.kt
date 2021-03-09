package com.ian.junemon.foodiepedia.ui

import android.os.Bundle
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.databinding.ActivityMainBinding
import com.ian.junemon.foodiepedia.databinding.ActivitySplashBinding
import com.ian.junemon.foodiepedia.util.activityViewBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class MainActivity : DaggerAppCompatActivity() {
    private val binding by activityViewBinding(ActivityMainBinding::inflate)
    @Inject
    lateinit var viewHelper: ViewHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewHelper) { fullScreenAnimation() }
    }
}