package com.example.junemon.foodapi_mvvm.ui.detailinformation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodListDataViewModel
import com.example.junemon.foodapi_mvvm.model.AreaFood
import com.example.junemon.foodapi_mvvm.model.CategoryFood
import com.example.junemon.foodapi_mvvm.model.IngredientFood
import com.example.junemon.foodapi_mvvm.ui.filter.FilterActivity
import com.example.junemon.foodapi_mvvm.util.*
import kotlinx.android.synthetic.main.activity_detail_information.*
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
        withViewModel({ DetailInformationPresenter(vm) }) {
            this.attachView(this@DetailInformationActivity, this@DetailInformationActivity)
            this.onCreate()
        }
    }

    override fun initView() {
    }


    override fun getAreaData(data: List<AreaFood.Meal>?) {
        data?.let {
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
            rvInformationIngredient.setUpWithGrid(it, R.layout.item_information_ingredient, 3, {
                with(this) {
                    tvInformationIngredient.text = it.strIngredient
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
            rvInformationCategory.setUpWithGrid(it, R.layout.item_information_category, 3, {
                with(this) {
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