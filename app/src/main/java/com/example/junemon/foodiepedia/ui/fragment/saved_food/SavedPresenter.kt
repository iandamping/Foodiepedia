package com.example.junemon.foodiepedia.ui.fragment.saved_food

import android.view.View
import androidx.lifecycle.Observer
import com.example.junemon.foodiepedia.FoodApp
import com.example.junemon.foodiepedia.base.BaseFragmentPresenter
import com.example.junemon.foodiepedia.base.OnGetLocalData
import com.example.junemon.foodiepedia.data.viewmodel.LocalDataViewModel
import com.example.junemon.foodiepedia.util.Constant

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

    override fun onCreateView(view: View) {
        view()?.initView(view)
    }
}