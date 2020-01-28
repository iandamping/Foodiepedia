package com.ian.junemon.foodiepedia.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.activityComponent
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.ActivitySplashBinding
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SplashActivity: AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private val mDelayHandler: Handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.run {
            lifecycleOwner = this@SplashActivity
            initView()
        }
        val view = binding.root
        setContentView(view)
        mDelayHandler.postDelayed(mRunnable, 3000L)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ActivitySplashBinding.initView(){
        apply {
            loadImageHelper.run {
                ivLoadSplash.loadWithGlide(ContextCompat.getDrawable(this@SplashActivity, R.drawable.splash)!!)
                ivLoadSplash2.loadWithGlide(ContextCompat.getDrawable(this@SplashActivity, R.drawable.splash_logo)!!)
            }
        }
    }
}