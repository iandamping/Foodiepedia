package com.ian.junemon.foodiepedia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ian.junemon.foodiepedia.activityComponent
import com.ian.junemon.foodiepedia.databinding.ActivityMainBinding

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class MainActivity : AppCompatActivity() {
    val mainActivityComponent by lazy { activityComponent().inject(this) }
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.run {
            lifecycleOwner = this@MainActivity
        }
        val view = binding.root
        setContentView(view)
    }
}