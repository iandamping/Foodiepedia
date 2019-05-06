package com.example.junemon.foodapi_mvvm.ui.allfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.ui.detail.DetailFoodActivity
import com.example.junemon.foodapi_mvvm.ui.detailinformation.DetailInformationActivity
import com.example.junemon.foodapi_mvvm.ui.discover.DiscoverActivity
import com.example.junemon.foodapi_mvvm.util.Constant.intentDetailKey
import com.example.junemon.foodapi_mvvm.util.fullScreenAnimation
import com.example.junemon.foodapi_mvvm.util.startActivity
import com.example.junemon.foodapi_mvvm.util.withViewModel
import kotlinx.android.synthetic.main.activity_main.*
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
        AllFoodAdapter(rvAllFood, data, R.layout.item_all_food) {
            startActivity<DetailFoodActivity> {
                putExtra(intentDetailKey, it.idMeal)
            }
        }
    }

    override fun initView() {
        ivDiscover.setOnClickListener {
            startActivity<DiscoverActivity>()
        }
        rela_bottom.setOnClickListener {
            startActivity<DetailInformationActivity>()

        }
    }


}
