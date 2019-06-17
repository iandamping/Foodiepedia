package com.ian.junemon.foodiepedia.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ian.app.helper.util.fullScreenAnimation
import com.ian.app.helper.util.switchFragment
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.ui.fragment.home.HomeFragment
import com.ian.junemon.foodiepedia.ui.fragment.profile.ProfileFragment
import com.ian.junemon.foodiepedia.ui.fragment.saved_food.SavedFragment
import com.ian.junemon.foodiepedia.util.Constant.switchBackToMain
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_main)

        initBottomNav()
        moveToSpesificFragment(intent?.getStringExtra(switchBackToMain))
    }

    private fun moveToSpesificFragment(dataCallback: String?) {
        when {
            dataCallback != null && dataCallback.contentEquals("3") -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, ProfileFragment())
                bottom_navigation.selectedItemId = R.id.navigation_profile
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, HomeFragment())
                true
            }
            R.id.navigation_saved -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, SavedFragment())
                true
            }

            R.id.navigation_profile -> {
                supportFragmentManager.switchFragment(null, R.id.main_container, ProfileFragment())
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initBottomNav() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.main_container, HomeFragment())
                .commit()
    }
}