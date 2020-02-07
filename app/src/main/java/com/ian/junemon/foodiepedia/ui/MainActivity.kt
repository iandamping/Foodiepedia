package com.ian.junemon.foodiepedia.ui

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ian.junemon.foodiepedia.FoodApp
import com.ian.junemon.foodiepedia.activityComponent
import com.ian.junemon.foodiepedia.databinding.ActivityMainBinding

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class MainActivity : AppCompatActivity() {
    val mainActivityComponent by lazy { (application as FoodApp).activityComponent }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        mainActivityComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}