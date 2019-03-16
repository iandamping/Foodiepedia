package com.example.junemon.foodapi_mvvm.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.DetailFoodViewModel
import com.example.junemon.foodapi_mvvm.model.DetailFood
import com.example.junemon.foodapi_mvvm.ui.adapter.IngredientAdapter
import com.example.junemon.foodapi_mvvm.ui.adapter.MeasurementAdapter
import com.example.junemon.foodapi_mvvm.util.Constant.intentDetailKey
import com.example.junemon.foodapi_mvvm.util.fullScreenAnimation
import com.example.junemon.foodapi_mvvm.util.loadUrl
import com.example.junemon.foodapi_mvvm.util.withViewModel
import kotlinx.android.synthetic.main.activity_detailed_food.*
import org.koin.android.viewmodel.ext.android.viewModel

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
        tvDetailedFoodCategory.text = "Food category : ${data.strCategory}"
        tvDetailedFoodArea.text = "Common food in ${data.strArea}"
        tvDetailedFoodInstruction.text = data.strInstructions
        toolbarDetailed.title = data.strMeal
    }

    override fun initView() {

    }

    override fun showIngredientData(dataIngredient: List<String>, dataMeasurement: List<String>) {
        rvDetailedIngredients.layoutManager = LinearLayoutManager(this)
        rvDetailedMeasurement.layoutManager = LinearLayoutManager(this)
        rvDetailedIngredients.isNestedScrollingEnabled = false
        rvDetailedMeasurement.isNestedScrollingEnabled = false
        dataIngredient.let { rvDetailedIngredients.adapter = IngredientAdapter(it) }
        dataMeasurement.let { rvDetailedMeasurement.adapter = MeasurementAdapter(it) }
    }


}