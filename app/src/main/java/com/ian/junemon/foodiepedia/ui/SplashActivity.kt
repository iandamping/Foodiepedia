package com.ian.junemon.foodiepedia.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.databinding.ActivitySplashBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SplashActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    @Inject
    lateinit var viewHelper: ViewHelper
    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewHelper.run { fullScreenAnimation() }
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.run {
            initView()
        }
        setContentView(binding.root)

        runDelayed {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun ActivitySplashBinding.initView() {
        loadImageHelper.run {
            ivLoadSplash.loadWithGlide(
                ContextCompat.getDrawable(
                    this@SplashActivity,
                    R.drawable.splash
                )!!
            )
        }
    }

    private fun runDelayed(call: () -> Unit) {
        lifecycleScope.launch {
            delay(500L)
            call.invoke()
        }
    }
}