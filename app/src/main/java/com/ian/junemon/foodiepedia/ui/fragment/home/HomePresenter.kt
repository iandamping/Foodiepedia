package com.ian.junemon.foodiepedia.ui.fragment.home

import android.view.View
import androidx.lifecycle.Observer
import com.ian.junemon.foodiepedia.base.*
import com.ian.junemon.foodiepedia.data.viewmodel.AllFoodViewModel

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
class HomePresenter(private val allFoodVM: AllFoodViewModel) : BaseFragmentPresenter<HomeView>() {

    override fun onAttach() {
        allFoodVM.getAllFoodData()
    }

    override fun onCreateView(view: View) {
        view()?.initView(view)

    }

    fun initAllData() {
        allFoodVM.liveDataState.observe(getLifeCycleOwner().viewLifecycleOwner, Observer {
            when (it) {
                is OnShowCategoryFoodDetail -> view()?.onGetAllFoodCategoryDetails(it.data)
                is OnShowAllFood -> view()?.onGetAllFood(it.data)
                is OnShowRandomFood -> view()?.onGetRandomFood(it.data?.get(0))
                is OnError -> view()?.onFailGetAllFoodCategoryDetails()
            }
        })
    }


}