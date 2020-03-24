package com.ian.junemon.foodiepedia.ui

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.databinding.ActivityMainBinding
import com.ian.junemon.foodiepedia.feature.di.activityComponent
import javax.inject.Inject

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewHelper: ViewHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewHelper.run { fullScreenAnimation() }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}