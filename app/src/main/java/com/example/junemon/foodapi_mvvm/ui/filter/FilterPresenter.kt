package com.example.junemon.foodapi_mvvm.ui.filter

import android.content.Intent
import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BasePresenter
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnShowFilterData
import com.example.junemon.foodapi_mvvm.data.viewmodel.FilterFoodViewModel
import com.example.junemon.foodapi_mvvm.util.Constant.areaType
import com.example.junemon.foodapi_mvvm.util.Constant.categoryType
import com.example.junemon.foodapi_mvvm.util.Constant.ingredientType
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
        if (intent.getStringExtra(categoryType).isNotBlank()) {
            vm.getIngredientFilterCategory(intent.getStringExtra(categoryType))
            return
        }
        if (intent.getStringExtra(areaType).isNotBlank()) {
            vm.getIngredientFilterArea(intent.getStringExtra(areaType))
            return
        }
        if (intent.getStringExtra(ingredientType).isNotBlank()) {
            vm.getIngredientFilterData(intent.getStringExtra(ingredientType))
            return
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