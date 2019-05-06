package com.example.junemon.foodapi_mvvm.ui.filter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.FilterFoodViewModel
import com.example.junemon.foodapi_mvvm.model.FilterFood
import com.example.junemon.foodapi_mvvm.util.loadUrl
import com.example.junemon.foodapi_mvvm.util.setUpWithGrid
import com.example.junemon.foodapi_mvvm.util.withViewModel
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.item_filter_food.view.*
import org.koin.android.viewmodel.ext.android.viewModel
/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class FilterActivity : AppCompatActivity(), FilterView {
    //    private lateinit var presenter: FilterPresenter
    private val vm: FilterFoodViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        withViewModel({ FilterPresenter(vm) }) {
            this.attachView(this@FilterActivity, this@FilterActivity)
            this.onCreate()
            this.getData(intent)
        }
//        presenter = initPresenter { FilterPresenter(vm) }.apply {
//            this.attachView(this@FilterActivity, this@FilterActivity)
//            this.onCreate()
//        }
//        presenter.getData(intent)
    }

    override fun onGetFilterData(data: List<FilterFood.Meal>) {
        rvFilterFood.setUpWithGrid(data, R.layout.item_filter_food, 2, {
            ivFilteringFood.loadUrl(it.strMealThumb)
            tvFilteringFoodCategory.text = it.strMeal
        })
    }

    override fun initView() {
    }

}