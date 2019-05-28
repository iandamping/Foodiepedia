package com.example.junemon.foodapi_mvvm.ui.home

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.model.DetailFood
import com.example.junemon.foodapi_mvvm.ui.detail.DetailFoodActivity
import com.example.junemon.foodapi_mvvm.ui.detailinformation.DetailInformationActivity
import com.example.junemon.foodapi_mvvm.ui.discover.DiscoverActivity
import com.example.junemon.foodapi_mvvm.ui.filter.FilterActivity
import com.example.junemon.foodapi_mvvm.ui.home.slideradapter.SliderItemAdapter
import com.example.junemon.foodapi_mvvm.util.*
import com.example.junemon.foodapi_mvvm.util.Constant.delayMillis
import com.example.junemon.foodapi_mvvm.util.Constant.intentDetailKey
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_home.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
class HomeActivity : AppCompatActivity(), HomeView {


    private val allFoodVM: AllFoodViewModel by viewModel()
    private var mHandler: Handler? = null
    private var pageSize: Int? = 0
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreenAnimation()
        setContentView(R.layout.activity_home)
        withViewModel({ HomePresenter(allFoodVM) }) {
            attachView(this@HomeActivity, this@HomeActivity)
            onCreate()
        }
        mHandler = Handler()
    }

    private var slideRunnable: Runnable = object : Runnable {
        override fun run() {
            if (currentPage == pageSize) {
                currentPage = 0
            }
            vpBestFood?.setCurrentItem(currentPage++, true)
            mHandler?.postDelayed(this, delayMillis)
        }
    }

    override fun onGetAllFood(data: List<AllFood.Meal>?) {
        pageSize = data?.size
        vpBestFood?.adapter = data?.let { SliderItemAdapter(it, this@HomeActivity) }
        indicator?.setViewPager(vpBestFood)
        if (mHandler != null) {
            mHandler?.removeCallbacks(slideRunnable)
        }
        mHandler?.postDelayed(slideRunnable, delayMillis)
    }

    override fun onGetAllFoodCategoryDetails(data: List<AllFoodCategoryDetail.Category>?) {
        shimmerHome?.stopShimmer()
        shimmerHome?.gone()
        data?.let { nonNullData ->
            rvFood.setUpHorizontal(nonNullData, R.layout.item_home, {
                with(this) {
                    tvHomeFoodName.text = it.strCategory
                    ivHomeFood.loadUrl(it.strCategoryThumb)
                }
            }, {
                startActivity<FilterActivity> {
                    putExtra(Constant.categoryType, this@setUpHorizontal.strCategory)
                }
            })
        }
    }

    override fun initView() {
        tvSeeAllCategoryFood.setOnClickListener {
            startActivity<DiscoverActivity>()
        }

        lnIcon2.setOnClickListener {
            startActivity<DetailInformationActivity> {
                putExtra(Constant.ingredientDetail, Constant.ingredientDetail)
            }
        }
        lnIcon3.setOnClickListener {
            startActivity<DetailInformationActivity> {
                putExtra(Constant.areaDetail, Constant.areaDetail)
            }
        }
    }

    override fun onGetRandomFood(data: DetailFood.Meal) {
        ivRandomFood.loadUrl(data.strMealThumb)
        lnIcon1.setOnClickListener {
            startActivity<DetailFoodActivity> {
                putExtra(intentDetailKey, data.idMeal)
            }
        }
    }

    override fun onFailGetAllFood() {
        shimmerHome?.stopShimmer()
        shimmerHome?.gone()
    }

    override fun onFailGetAllFoodCategoryDetails() {
        shimmerHome?.stopShimmer()
        shimmerHome?.gone()
    }

    override fun onStart() {
        super.onStart()
        mHandler?.postDelayed(slideRunnable, 4000)
    }

    override fun onStop() {
        super.onStop()
        mHandler?.removeCallbacks(slideRunnable)
    }

    override fun onPause() {
        super.onPause()
        shimmerHome?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        shimmerHome?.startShimmer()
    }
}