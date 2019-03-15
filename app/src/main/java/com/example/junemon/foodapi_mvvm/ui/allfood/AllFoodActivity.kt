package com.example.junemon.foodapi_mvvm.ui.allfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.ui.detail.DetailFoodActivity
import com.example.junemon.foodapi_mvvm.ui.discover.DiscoverActivity
import com.example.junemon.foodapi_mvvm.util.Constant.intentDetailKey
import com.example.junemon.foodapi_mvvm.util.fullScreenAnimation
import com.example.junemon.foodapi_mvvm.util.startActivity
import com.example.junemon.foodapi_mvvm.util.startActivityWithString
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class AllFoodActivity : AppCompatActivity(), AllFoodView {

    private val vm: AllFoodViewModel by viewModel()
    private lateinit var presenter: AllFoodPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
        presenter = AllFoodPresenter(vm, this)
        presenter.onCreate(this)
        presenter.setData()
    }

    override fun getDetailFood(data: List<AllFood.Meal>?) {
        rvAllFood.layoutManager = SkidRightLayoutManager(1.5f, 0.85f)
        data?.let {
            rvAllFood.adapter = AllFoodAdapter(it) { detail ->
                startActivityWithString<DetailFoodActivity>(intentDetailKey, detail.idMeal)
            }
        }

    }

    override fun initView() {
        ivDiscover.setOnClickListener {
            startActivity<DiscoverActivity>()
        }
    }


}
