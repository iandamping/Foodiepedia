package com.example.junemon.foodapi_mvvm.ui.detailinformation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodListDataViewModel
import com.example.junemon.foodapi_mvvm.model.AreaFood
import com.example.junemon.foodapi_mvvm.model.CategoryFood
import com.example.junemon.foodapi_mvvm.model.IngredientFood
import com.example.junemon.foodapi_mvvm.ui.adapter.InformationAreaAdapter
import com.example.junemon.foodapi_mvvm.ui.adapter.InformationFoodAdapter
import com.example.junemon.foodapi_mvvm.ui.adapter.InformationIngredientAdapter
import com.example.junemon.foodapi_mvvm.util.fullScreenAnimation
import com.example.junemon.foodapi_mvvm.util.initPresenter
import kotlinx.android.synthetic.main.activity_detail_information.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailInformationActivity : AppCompatActivity(), DetailInformationView {
    private val vm: AllFoodListDataViewModel by viewModel()
    private lateinit var presenter: DetailInformationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_detail_information)
        presenter = initPresenter { DetailInformationPresenter(vm) }.apply {
            this.attachView(this@DetailInformationActivity, this@DetailInformationActivity)
            this.onCreate()

        }
    }

    override fun initView() {
    }


    override fun getAreaData(data: List<AreaFood.Meal>?) {
        InformationAreaAdapter(rvInformationArea, data, R.layout.item_information_area) {}
    }

    override fun getIngredientData(data: List<IngredientFood.Meal>?) {
        InformationIngredientAdapter(rvInformationIngredient, data, R.layout.item_information_ingredient) {}
    }

    override fun getCategoryData(data: List<CategoryFood.Meal>?) {
        InformationFoodAdapter(rvInformationCategory, data, R.layout.item_information_category) {}
    }

}