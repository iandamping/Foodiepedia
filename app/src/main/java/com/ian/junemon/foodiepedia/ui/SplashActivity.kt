package com.ian.junemon.foodiepedia.ui

import android.content.Intent
import android.os.Bundle
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseActivity
import com.ian.junemon.foodiepedia.databinding.ActivitySplashBinding
import com.ian.junemon.foodiepedia.util.activityViewBinding
import com.ian.junemon.foodiepedia.util.getDrawables
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private val binding by activityViewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.initView()

        runDelayed {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ActivitySplashBinding.initView() {
        with(loadImageHelper) {
            ivLoadSplash.loadWithGlide(
                getDrawables(R.drawable.splash)
            )
        }
    }

    private fun runDelayed(call: () -> Unit) {
        consumeSuspend {
            delay(500L)
            call.invoke()
        }
    }
}