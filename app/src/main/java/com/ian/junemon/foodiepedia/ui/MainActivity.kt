package com.ian.junemon.foodiepedia.ui

import android.os.Bundle
import com.ian.junemon.foodiepedia.base.BaseActivity
import com.ian.junemon.foodiepedia.core.presentation.view.ViewHelper
import com.ian.junemon.foodiepedia.databinding.ActivityMainBinding
import com.ian.junemon.foodiepedia.injection.DefaultFragmentFactoryEntryPoint
import com.ian.junemon.foodiepedia.util.activityViewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewHelper: ViewHelper

    private val binding by activityViewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromActivity(
            this,
            DefaultFragmentFactoryEntryPoint::class.java
        )

        supportFragmentManager.fragmentFactory = entryPoint.getFragmentFactory()
        super.onCreate(savedInstanceState)
        viewHelper.fullScreenAnimation(this)
    }
}