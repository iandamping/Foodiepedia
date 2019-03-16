package com.example.junemon.foodapi_mvvm.ui.allfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodListDataViewModel
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.model.AreaFood
import com.example.junemon.foodapi_mvvm.model.CategoryFood
import com.example.junemon.foodapi_mvvm.model.IngredientFood
import com.example.junemon.foodapi_mvvm.ui.detail.DetailFoodActivity
import com.example.junemon.foodapi_mvvm.ui.discover.DiscoverActivity
import com.example.junemon.foodapi_mvvm.util.Constant.intentDetailKey
import com.example.junemon.foodapi_mvvm.util.fullScreenAnimation
import com.example.junemon.foodapi_mvvm.util.logD
import com.example.junemon.foodapi_mvvm.util.startActivity
import com.example.junemon.foodapi_mvvm.util.withViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class AllFoodActivity : AppCompatActivity(), AllFoodView {
    private val listVm: AllFoodListDataViewModel by viewModel()
    private val vm: AllFoodViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_main)
        withViewModel({ AllFoodPresenter(vm, listVm) }) {
            this.attachView(this@AllFoodActivity, this@AllFoodActivity)
            this.onCreate()
        }
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)

    }

    override fun getDetailFood(data: List<AllFood.Meal>?) {
        rvAllFood.layoutManager = SkidRightLayoutManager(1.5f, 0.85f)
        data?.let {
            rvAllFood.adapter = AllFoodAdapter(it) { detail ->
                startActivity<DetailFoodActivity> {
                    putExtra(intentDetailKey, detail.idMeal)
                }
            }
        }

    }

    override fun initView() {
        ivDiscover.setOnClickListener {
            startActivity<DiscoverActivity>()
        }
    }

    override fun getAreaData(data: List<AreaFood.Meal>?) {
        data?.forEach {
            logD(it.strArea)
        }
    }

    override fun getIngredientData(data: List<IngredientFood.Meal>?) {
        data?.forEach {
            logD(it.strDescription)
        }
    }

    override fun getCategoryData(data: List<CategoryFood.Meal>?) {
        data?.forEach {
            logD(it.category)
        }
    }


}
