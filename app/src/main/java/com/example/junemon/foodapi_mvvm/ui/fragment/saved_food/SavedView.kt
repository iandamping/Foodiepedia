package com.example.junemon.foodapi_mvvm.ui.fragment.saved_food

import com.example.junemon.foodapi_mvvm.base.BaseFragmentView
import com.example.junemon.foodapi_mvvm.data.local_data.LocalFoodData

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
interface SavedView : BaseFragmentView {
    fun onSuccesGetLocalData(data: List<LocalFoodData>)
}