package com.example.junemon.foodapi_mvvm.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.DetailFoodViewModel
import com.example.junemon.foodapi_mvvm.model.DetailFood
import com.example.junemon.foodapi_mvvm.util.Constant.intentDetailKey
import com.example.junemon.foodapi_mvvm.util.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFoodActivity : AppCompatActivity(), DetailFoodView {
    private val vm: DetailFoodViewModel by viewModel()
    private lateinit var presenter: DetailFoodPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
        presenter = DetailFoodPresenter(vm, this)
        presenter.onCreate(this)
        onNewIntent(intent)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.setData(intent?.getStringExtra(intentDetailKey))
    }

    override fun showDetailData(data: DetailFood.Meal) {
        ivDetailFood.loadUrl(data.strMealThumb)
        tvDetailFoodName.text = data.strMeal
        tvDetailFoodCategory.text = data.strCategory
        tvDetailFoodArea.text = data.strArea
        tvDetailFoodInstruction.text = data.strInstructions
    }

    override fun initView() {
    }


}