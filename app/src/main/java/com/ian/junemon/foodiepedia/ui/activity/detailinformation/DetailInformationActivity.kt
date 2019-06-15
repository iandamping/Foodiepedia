package com.ian.junemon.foodiepedia.ui.activity.detailinformation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.data.viewmodel.AllFoodListDataViewModel
import com.ian.junemon.foodiepedia.model.AreaFood
import com.ian.junemon.foodiepedia.model.CategoryFood
import com.ian.junemon.foodiepedia.model.IngredientFood
import com.ian.junemon.foodiepedia.ui.activity.filter.FilterActivity
import com.ian.junemon.foodiepedia.util.Constant
import com.ian.junemon.foodiepedia.util.withViewModel
import com.ian.app.helper.util.*
import com.ian.recyclerviewhelper.helper.setUpWithGrid
import kotlinx.android.synthetic.main.activity_detail_information.*
import kotlinx.android.synthetic.main.activity_discover.*
import kotlinx.android.synthetic.main.item_information_area.view.*
import kotlinx.android.synthetic.main.item_information_category.view.*
import kotlinx.android.synthetic.main.item_information_ingredient.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class DetailInformationActivity : AppCompatActivity(), DetailInformationView {

    private val vm: AllFoodListDataViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_detail_information)
        initAdsView()
        withViewModel({ DetailInformationPresenter(vm) }) {
            attachView(this@DetailInformationActivity, this@DetailInformationActivity)
            onCreate()
            getData(intent)
        }
    }

    private fun initAdsView() {
        val request = AdRequest.Builder().build()
        detailInformationAdView.loadAd(request)
    }

    override fun initView() {
        shimmer_home
    }

    override fun onPause() {
        super.onPause()
        shimmer_home?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        shimmer_home?.startShimmer()
    }


    override fun getAreaData(data: List<AreaFood.Meal>?) {
        data?.let {
            shimmer_home?.stopShimmer()
            shimmer_home?.gone()
            lnAreaSearch.visible()
            rvInformationArea.setUpWithGrid(it, R.layout.item_information_area, 3, {
                with(this) {
                    tvDescriptionArea.text = it.strArea
                }
            }, {
                startActivity<FilterActivity> {
                    putExtra(Constant.areaType, strArea)
                }
            })
        }
    }

    override fun getIngredientData(data: List<IngredientFood.Meal>?) {
        data?.let {
            shimmer_home?.stopShimmer()
            shimmer_home?.gone()
            lnIngredientsSearch.visible()
            rvInformationIngredient.setUpWithGrid(it, R.layout.item_information_ingredient, 3, {
                with(this) {
                    if (it.strIngredient?.length!! >= 12) {
                        val tmp = it.strIngredient.substring(0, 12) + " ..."
                        tvInformationIngredient.text = tmp
                    } else {
                        tvInformationIngredient.text = it.strIngredient
                    }
                    ivIngredientImages.loadResizeWithGlide(resources.getString(R.string.ingredient_images_helper) + it.strIngredient + "-Small.png")
                }
            }, {
                startActivity<FilterActivity> {
                    putExtra(Constant.ingredientType, strIngredient)
                }
            })
        }
    }

    override fun getCategoryData(data: List<CategoryFood.Meal>?) {
        data?.let {
            shimmer_home?.stopShimmer()
            shimmer_home?.gone()
            lnCategorySearch.visible()
            rvInformationCategory.setUpWithGrid(it, R.layout.item_information_category, 3, {
                with(this) {
                    ivDescriptionImages.loadWithGlide(resources.getString(R.string.category_images_helper) + it.category + ".png")
                    tvDescriptionCategory.text = it.category
                }
            }, {
                startActivity<FilterActivity> {
                    putExtra(Constant.categoryType, category)
                }
            })
        }
    }

}