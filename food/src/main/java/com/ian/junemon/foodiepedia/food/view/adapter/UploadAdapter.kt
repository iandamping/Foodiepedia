package com.ian.junemon.foodiepedia.food.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ian.junemon.foodiepedia.food.view.upload.FragmentIngredientFood
import com.ian.junemon.foodiepedia.food.view.upload.FragmentInstructionFood
import com.ian.junemon.foodiepedia.food.view.upload.FragmentNameFood

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val pageCount: Int = 3
    private val tabTitle = arrayOf("Name", "Ingredients", "Instruction")
    override fun getItem(position: Int): Fragment {
        var fragments: Fragment = FragmentNameFood()
        when (position) {
            1 -> fragments = FragmentIngredientFood()
            2 -> fragments = FragmentInstructionFood()
        }
        return fragments
    }

    override fun getCount(): Int {
        return pageCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitle[position]
    }
}