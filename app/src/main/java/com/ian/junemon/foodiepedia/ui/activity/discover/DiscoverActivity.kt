package com.ian.junemon.foodiepedia.ui.activity.discover

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.ian.app.helper.util.fullScreenAnimation
import com.ian.app.helper.util.loadWithGlide
import com.ian.app.helper.util.startActivity
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.data.viewmodel.AllFoodCategoryViewModel
import com.ian.junemon.foodiepedia.model.AllFoodCategoryDetail
import com.ian.junemon.foodiepedia.ui.activity.filter.FilterActivity
import com.ian.junemon.foodiepedia.util.Constant
import com.ian.junemon.foodiepedia.util.withViewModel
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
        initAdsView()
        withViewModel({ DiscoverPresenter(defaultVm) }) {
            this.attachView(this@DiscoverActivity, this@DiscoverActivity)
            this.onCreate()
        }
    }

    private fun initAdsView() {
        val request = AdRequest.Builder().build()
        discoverAdView.loadAd(request)
    }

    override fun onShowDefaultFoodCategory(data: List<AllFoodCategoryDetail.Category>?) {
        data?.let {
            rvDiscoverFood.setUpWithGrid(it, R.layout.item_discover_food, 2, {
                with(this) {
                    tvDiscoverFoodCategory.text = it.strCategory
                    tvDiscoverFoodDescription.text = it.strCategoryDescription
                    ivDiscoverFood.loadWithGlide(it.strCategoryThumb)
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