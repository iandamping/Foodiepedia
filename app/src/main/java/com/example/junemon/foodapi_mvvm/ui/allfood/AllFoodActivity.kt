package com.example.junemon.foodapi_mvvm.ui.allfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.ui.detail.DetailFoodActivity
import com.example.junemon.foodapi_mvvm.ui.detailinformation.DetailInformationActivity
import com.example.junemon.foodapi_mvvm.ui.discover.DiscoverActivity
import com.example.junemon.foodapi_mvvm.util.*
import com.example.junemon.foodapi_mvvm.util.Constant.areaDetail
import com.example.junemon.foodapi_mvvm.util.Constant.categoryDetail
import com.example.junemon.foodapi_mvvm.util.Constant.goingToDetail
import com.example.junemon.foodapi_mvvm.util.Constant.ingredientDetail
import com.example.junemon.foodapi_mvvm.util.Constant.intentDetailKey
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_all_food.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class AllFoodActivity : AppCompatActivity(), AllFoodView {
    private val vm: AllFoodViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_main)
        withViewModel({ AllFoodPresenter(vm) }) {
            this.attachView(this@AllFoodActivity, this@AllFoodActivity)
            this.onCreate()
        }
    }

    override fun getDetailFood(data: List<AllFood.Meal>?) {
        data?.let {
            rvAllFood.setUpWithSkid(it, R.layout.item_all_food, {
                with(this) {
                    tvAllFoodName.text = it.strMeal
                    tvAllFoodCategory.text = it.strCategory
                    tvAllFoodArea.text = it.strArea
                    ivAllFood.loadUrl(it.strMealThumb)
                }
            }, {
                startActivity<DetailFoodActivity> {
                    putExtra(intentDetailKey, it.idMeal)
                }
            })
        }

    }

    override fun initView() {
        ivDiscover.setOnClickListener {
            startActivity<DiscoverActivity>()
        }

        lnCategory.setOnClickListener {
            startActivity<DetailInformationActivity> {
                putExtra(categoryDetail, categoryDetail)
            }
        }
        lnIngredients.setOnClickListener {
            startActivity<DetailInformationActivity> {
                putExtra(ingredientDetail, ingredientDetail)
            }
        }
        lnArea.setOnClickListener {
            startActivity<DetailInformationActivity> {
                putExtra(areaDetail, areaDetail)
            }
        }
    }


}
