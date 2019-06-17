package com.ian.junemon.foodiepedia.ui.fragment.saved_food

import com.ian.junemon.foodiepedia.base.BaseFragmentView
import com.ian.junemon.foodiepedia.data.local_data.detail.LocalFoodData

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
interface SavedView : BaseFragmentView {
    fun onSuccesGetLocalData(data: List<LocalFoodData>)
}