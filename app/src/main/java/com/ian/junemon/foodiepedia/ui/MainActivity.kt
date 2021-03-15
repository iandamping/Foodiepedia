package com.ian.junemon.foodiepedia.ui

import com.ian.junemon.foodiepedia.base.BaseActivity
import com.ian.junemon.foodiepedia.databinding.ActivityMainBinding
import com.ian.junemon.foodiepedia.util.activityViewBinding
import dagger.android.support.DaggerAppCompatActivity

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class MainActivity : BaseActivity() {
    private val binding by activityViewBinding(ActivityMainBinding::inflate)
}