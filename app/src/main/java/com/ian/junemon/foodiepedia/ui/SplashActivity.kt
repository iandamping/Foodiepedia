package com.ian.junemon.foodiepedia.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.databinding.ActivitySplashBinding
import com.ian.junemon.foodiepedia.util.activityViewBinding
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
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
    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private val binding by activityViewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewHelper) {
            fullScreenAnimation()
        }
        binding.initView()

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