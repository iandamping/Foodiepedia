package com.example.junemon.foodapi_mvvm.ui.activity.discover

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodCategoryViewModel
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.ui.activity.filter.FilterActivity
import com.example.junemon.foodapi_mvvm.util.Constant
import com.example.junemon.foodapi_mvvm.util.withViewModel
import com.ian.app.helper.util.fullScreenAnimation
import com.ian.app.helper.util.loadWithGlide
import com.ian.app.helper.util.startActivity
import com.ian.recyclerviewhelper.helper.setUpWithGrid
import kotlinx.android.synthetic.main.activity_discover.*
import kotlinx.android.synthetic.main.item_discover_food.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

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
        data?.let {
            rvDiscoverFood.setUpWithGrid(it, R.layout.item_discover_food, 2, {
                with(this) {
                    tvDiscoverFoodCategory.text = it.strCategory
                    tvDiscoverFoodDescription.text = it.strCategoryDescription
                    ivDiscoverFood.loadWithGlide(it.strCategoryThumb, this@DiscoverActivity)
                }
            }, {
                startActivity<FilterActivity> {
                    putExtra(Constant.categoryType, this@setUpWithGrid.strCategory)
                }
            })
        }
    }

    override fun initView() {
    }


}