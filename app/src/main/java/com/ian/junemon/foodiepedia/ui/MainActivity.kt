package com.ian.junemon.foodiepedia.ui

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ian.junemon.foodiepedia.databinding.ActivityMainBinding
import com.ian.junemon.foodiepedia.feature.di.activityComponent

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent().inject(this)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}