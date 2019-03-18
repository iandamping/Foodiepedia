package com.example.junemon.foodapi_mvvm.ui.discover

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodCategoryViewModel
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.util.fullScreenAnimation
import com.example.junemon.foodapi_mvvm.util.withViewModel
import kotlinx.android.synthetic.main.activity_discover.*
import org.koin.android.viewmodel.ext.android.viewModel

class DiscoverActivity : AppCompatActivity(), DiscoverView {
    private val defaultVm: AllFoodCategoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_discover)
        withViewModel({ DiscoverPresenter(defaultVm) }) {
            this.attachView(this@DiscoverActivity, this@DiscoverActivity)
            this.onCreate()
        }
    }

    override fun onShowDefaultFoodCategory(data: List<AllFoodCategoryDetail.Category>?) {
        DiscoverAdapter(rvDiscoverFood,data,R.layout.item_discover_food){
        }
    }

    override fun initView() {
    }


}