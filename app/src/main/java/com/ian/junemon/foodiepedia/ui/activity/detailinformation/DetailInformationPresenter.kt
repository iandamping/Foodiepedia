package com.ian.junemon.foodiepedia.ui.activity.detailinformation

import android.content.Intent
import androidx.lifecycle.Observer
import com.ian.junemon.foodiepedia.base.BasePresenter
import com.ian.junemon.foodiepedia.base.OnShowAreaFood
import com.ian.junemon.foodiepedia.base.OnShowIngredientFood
import com.ian.junemon.foodiepedia.data.viewmodel.AllFoodListDataViewModel
import com.ian.junemon.foodiepedia.util.Constant.areaDetail
import com.ian.junemon.foodiepedia.util.Constant.ingredientDetail

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class DetailInformationPresenter(private val vm: AllFoodListDataViewModel) : BasePresenter<DetailInformationView>() {

    override fun onCreate() {
        view()?.initView()

    }

    fun getData(intent: Intent) {
        val tmpIngredientData = intent.getStringExtra(ingredientDetail)
        val tmpAreaData = intent.getStringExtra(areaDetail)
        when {
            !tmpAreaData.isNullOrEmpty() -> vm.getLocalAreaData()
            !tmpIngredientData.isNullOrEmpty() -> vm.getLocalIngredientData()
        }

        vm.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
                is OnShowAreaFood -> {
                    it.data?.observe(getLifeCycleOwner(), Observer { data ->
                        if (data.isEmpty()) {
                            if (!tmpAreaData.isNullOrEmpty()) {
                                vm.getAreaData()
                            }
                        } else view()?.getAreaData(data)

                    })
                }

                is OnShowIngredientFood -> {
                    it.data?.observe(getLifeCycleOwner(), Observer { data ->
                        if (data.isEmpty()) {
                            if (!tmpIngredientData.isNullOrEmpty()) {
                                vm.getIngredientData()
                            }
                        } else view()?.getIngredientData(data)

                    })
                }
            }
        })
    }

}