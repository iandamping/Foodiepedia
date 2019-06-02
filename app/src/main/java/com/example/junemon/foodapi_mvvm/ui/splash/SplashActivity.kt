package com.example.junemon.foodapi_mvvm.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.ui.home.HomeActivity
import com.example.junemon.foodapi_mvvm.util.Constant.delayMillis
import com.ian.app.helper.util.fullScreenAnimation
import com.ian.app.helper.util.startActivity

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class SplashActivity : AppCompatActivity() {
    private lateinit var mHandler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_splash)
        mHandler = Handler()
        mHandler.postDelayed(mRunnable, delayMillis)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity<HomeActivity>()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable)
        }
    }
}