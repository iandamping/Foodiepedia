package com.example.junemon.foodapi_mvvm.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.ui.allfood.AllFoodActivity
import com.example.junemon.foodapi_mvvm.util.startActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var mHandler: Handler
    private val splashDelay = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler = Handler()
        mHandler.postDelayed(mRunnable, splashDelay)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity<AllFoodActivity>()
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