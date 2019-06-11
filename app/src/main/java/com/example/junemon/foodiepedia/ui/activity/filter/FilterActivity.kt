package com.example.junemon.foodiepedia.ui.activity.filter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodiepedia.R
import com.example.junemon.foodiepedia.data.viewmodel.FilterFoodViewModel
import com.example.junemon.foodiepedia.model.FilterFood
import com.example.junemon.foodiepedia.ui.activity.detail.DetailFoodActivity
import com.example.junemon.foodiepedia.util.Constant
import com.example.junemon.foodiepedia.util.withViewModel
import com.ian.app.helper.util.fullScreenAnimation
import com.ian.app.helper.util.loadWithGlide
import com.ian.app.helper.util.startActivity
import com.ian.recyclerviewhelper.helper.setUpWithGrid
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
        fullScreenAnimation()
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
            ivFilteringFood.loadWithGlide(it.strMealThumb)
            if (it.strMeal?.length!! >= 12) {
                val tmp = it.strMeal.substring(0, 12) + " ..."
                tvFilteringFoodCategory.text = tmp
            } else {
                tvFilteringFoodCategory.text = it.strMeal
            }

        }, {
            startActivity<DetailFoodActivity> {
                putExtra(Constant.intentDetailKey, idMeal)
            }
        })
    }

    override fun initView() {
    }

}