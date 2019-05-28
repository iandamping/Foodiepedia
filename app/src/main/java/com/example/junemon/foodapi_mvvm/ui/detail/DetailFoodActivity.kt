package com.example.junemon.foodapi_mvvm.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.DetailFoodViewModel
import com.example.junemon.foodapi_mvvm.model.DetailFood
import com.example.junemon.foodapi_mvvm.util.*
import com.example.junemon.foodapi_mvvm.util.Constant.intentDetailKey
import kotlinx.android.synthetic.main.activity_detailed_food.*
import kotlinx.android.synthetic.main.item_ingredient_adapter.view.*
import kotlinx.android.synthetic.main.item_measurement_adapter.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class DetailFoodActivity : AppCompatActivity(), DetailFoodView {
    private val vm: DetailFoodViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_detailed_food)
        withViewModel({ DetailFoodPresenter(vm) }) {
            this.attachView(this@DetailFoodActivity, this@DetailFoodActivity)
            this.onCreate()
            this.setData(intent?.getStringExtra(intentDetailKey))
        }
    }


    override fun showDetailData(data: DetailFood.Meal) {
        ivDetailedFood.loadUrl(data.strMealThumb)
        ivDetailedFood.setOnClickListener {
            fullScreen(data.strMealThumb)
        }
        tvDetailedFoodCategory.text = "Food category : ${data.strCategory}"
        tvDetailedFoodArea.text = "Common food in ${data.strArea}"
        tvDetailedFoodInstruction.text = data.strInstructions
        toolbarDetailed.title = data.strMeal
    }

    override fun initView() {

    }

    override fun showIngredientData(dataIngredient: List<String>, dataMeasurement: List<String>) {
        rvDetailedIngredients.isNestedScrollingEnabled = false
        rvDetailedMeasurement.isNestedScrollingEnabled = false


        dataIngredient.let { data ->
            rvDetailedIngredients.setUp(data, R.layout.item_ingredient_adapter, {
                this@setUp.tvIngredientAdapter.text = it
            })
        }
        dataMeasurement.let { data ->
            rvDetailedMeasurement.setUp(data, R.layout.item_measurement_adapter, {
                this@setUp.tvMeasurementAdapter.text = it
            })
        }
    }


}