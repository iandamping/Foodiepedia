package com.ian.junemon.foodiepedia.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.activityComponent
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.worker.DataFetcherWorker
import com.ian.junemon.foodiepedia.databinding.ActivitySplashBinding
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    @Inject
    lateinit var permissionHelper: PermissionHelper
    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private val mDelayHandler: Handler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent().inject(this)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.run {
            initView()
        }
        setContentView(binding.root)
        permissionHelper.getAllPermission(this) {
            if (it) {
                mDelayHandler.postDelayed(mRunnable, 3000L)
            }
        }
        /*workerManagerState(this)*/
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ActivitySplashBinding.initView() {
        apply {
            loadImageHelper.run {
                ivLoadSplash.loadWithGlide(ContextCompat.getDrawable(this@SplashActivity, R.drawable.splash)!!)
                ivLoadSplash2.loadWithGlide(ContextCompat.getDrawable(this@SplashActivity, R.drawable.splash_logo)!!)
            }
        }
    }

    private fun workerManagerState(context: Context) {
        WorkManager.getInstance(context).getWorkInfosByTagLiveData(DataFetcherWorker.WORK_NAME).observe(this,
            Observer { workInfo ->
                if (workInfo != null && workInfo.isNotEmpty()) {
                    workInfo.forEach {
                        if (it.state == WorkInfo.State.SUCCEEDED) {
                            mDelayHandler.postDelayed(mRunnable, 3000L)
                        }
                    }
                }
            })
    }
}