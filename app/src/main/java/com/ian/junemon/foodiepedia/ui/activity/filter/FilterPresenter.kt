package com.ian.junemon.foodiepedia.ui.activity.filter

import android.content.Intent
import androidx.lifecycle.Observer
import com.ian.junemon.foodiepedia.base.BasePresenter
import com.ian.junemon.foodiepedia.base.OnComplete
import com.ian.junemon.foodiepedia.base.OnShowFilterData
import com.ian.junemon.foodiepedia.data.viewmodel.FilterFoodViewModel
import com.ian.junemon.foodiepedia.util.Constant.areaType
import com.ian.junemon.foodiepedia.util.Constant.categoryType
import com.ian.junemon.foodiepedia.util.Constant.ingredientType

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class FilterPresenter(private val vm: FilterFoodViewModel) : BasePresenter<FilterView>() {

    override fun onCreate() {
        view()?.initView()
        initGetData()
    }

    fun getData(intent: Intent) {
        val tmpCategoryData = intent.getStringExtra(categoryType)
        val tmpAreaData = intent.getStringExtra(areaType)
        val tmpIngredientData = intent.getStringExtra(ingredientType)
        when {
            !tmpCategoryData.isNullOrEmpty() -> vm.getIngredientFilterCategory(tmpCategoryData)
            !tmpAreaData.isNullOrEmpty() -> vm.getIngredientFilterArea(tmpAreaData)
            !tmpIngredientData.isNullOrEmpty() -> vm.getIngredientFilterData(tmpIngredientData)
        }
    }

    private fun initGetData() {
        vm.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
                is OnShowFilterData -> view()?.onGetFilterData(it.data)
                is OnComplete -> setDialogShow(it.show)
            }
        })
    }
}