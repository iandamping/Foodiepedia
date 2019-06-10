package com.example.junemon.foodiepedia.ui.fragment.saved_food

import com.example.junemon.foodiepedia.base.BaseFragmentView
import com.example.junemon.foodiepedia.data.local_data.LocalFoodData

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
interface SavedView : BaseFragmentView {
    fun onSuccesGetLocalData(data: List<LocalFoodData>)
}