package com.ian.junemon.foodiepedia.ui.activity.detailinformation

import android.content.Intent
import androidx.lifecycle.Observer
import com.ian.junemon.foodiepedia.base.BasePresenter
import com.ian.junemon.foodiepedia.base.OnShowAreaFood
import com.ian.junemon.foodiepedia.base.OnShowCategoryFood
import com.ian.junemon.foodiepedia.base.OnShowIngredientFood
import com.ian.junemon.foodiepedia.data.viewmodel.AllFoodListDataViewModel
import com.ian.junemon.foodiepedia.util.Constant.areaDetail
import com.ian.junemon.foodiepedia.util.Constant.categoryDetail
import com.ian.junemon.foodiepedia.util.Constant.ingredientDetail

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class DetailInformationPresenter(private val vm: AllFoodListDataViewModel) : BasePresenter<DetailInformationView>() {

    override fun onCreate() {
        view()?.initView()
        initData()
    }

    fun getData(intent: Intent) {
        val tmpCategoryData = intent.getStringExtra(categoryDetail)
        val tmpAreaData = intent.getStringExtra(areaDetail)
        val tmpIngredientData = intent.getStringExtra(ingredientDetail)
        when {
            !tmpCategoryData.isNullOrEmpty() -> vm.getCategoryData()
            !tmpAreaData.isNullOrEmpty() -> vm.getAreaData()
            !tmpIngredientData.isNullOrEmpty() -> vm.getIngredientData()
        }
    }


    private fun initData() {
        vm.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {

                is OnShowAreaFood -> {
                    view()?.getAreaData(it.data)
                }
                is OnShowCategoryFood -> {
                    view()?.getCategoryData(it.data)
                }
                is OnShowIngredientFood -> {
                    view()?.getIngredientData(it.data)
                }
            }
        })
    }
}