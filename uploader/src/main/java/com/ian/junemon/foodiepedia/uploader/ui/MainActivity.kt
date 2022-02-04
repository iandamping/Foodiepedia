package com.ian.junemon.foodiepedia.uploader.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ian.junemon.foodiepedia.core.presentation.view.ViewHelper
import com.ian.junemon.foodiepedia.uploader.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewHelper: ViewHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewHelper.fullScreenAnimation(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}