package com.ian.junemon.foodiepedia.ui.fragment.saved_food

import android.view.View
import androidx.lifecycle.Observer
import com.ian.junemon.foodiepedia.FoodApp
import com.ian.junemon.foodiepedia.base.BaseFragmentPresenter
import com.ian.junemon.foodiepedia.base.OnGetLocalData
import com.ian.junemon.foodiepedia.data.viewmodel.LocalDataViewModel
import com.ian.junemon.foodiepedia.util.Constant

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class SavedPresenter(private val vm: LocalDataViewModel) : BaseFragmentPresenter<SavedView>() {

    override fun onAttach() {
    }

    fun initLocalData() {
        if (!FoodApp.prefHelper.getStringInSharedPreference(Constant.saveUserProfile).isNullOrBlank()) {
            vm.getLocalData().apply {
                vm.liveDataState.observe(getLifeCycleOwner().viewLifecycleOwner, Observer {
                    when (it) {
                        is OnGetLocalData -> it.data.observe(getLifeCycleOwner().viewLifecycleOwner, Observer { localData ->
                            if (localData.isEmpty()) {
                                view()?.onFailedGetData("Empty")
                            } else {
                                view()?.onSuccesGetLocalData(localData)
                            }
                        })
                    }
                })
            }
        } else {
            view()?.onFailedGetData("Empty")
        }
    }

    fun deleteSelectedFood(foodID: Int?) {
        if (foodID != null) vm.deleteSelectedLocalData(foodID)
    }

    override fun onCreateView(view: View) {
        view()?.initView(view)
    }
}