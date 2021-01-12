package com.ian.junemon.foodiepedia.uploader.ui

import android.os.Bundle
import com.ian.junemon.foodiepedia.uploader.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.uploader.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewHelper: ViewHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewHelper.run { fullScreenAnimation() }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}