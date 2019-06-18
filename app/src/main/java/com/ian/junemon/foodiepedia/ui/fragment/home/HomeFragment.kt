package com.ian.junemon.foodiepedia.ui.fragment.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ian.app.helper.util.*
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.data.local_data.all_food.LocalAllFoodData
import com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail.LocalAllFoodCategoryDetailData
import com.ian.junemon.foodiepedia.data.viewmodel.AllFoodViewModel
import com.ian.junemon.foodiepedia.model.DetailFood
import com.ian.junemon.foodiepedia.ui.activity.detail.DetailFoodActivity
import com.ian.junemon.foodiepedia.ui.activity.detailinformation.DetailInformationActivity
import com.ian.junemon.foodiepedia.ui.activity.discover.DiscoverActivity
import com.ian.junemon.foodiepedia.ui.activity.filter.FilterActivity
import com.ian.junemon.foodiepedia.ui.fragment.home.slideradapter.SliderItemAdapter
import com.ian.junemon.foodiepedia.util.Constant
import com.ian.junemon.foodiepedia.util.Constant.delayMillis
import com.ian.junemon.foodiepedia.util.Constant.intentDetailKey
import com.ian.junemon.foodiepedia.util.withViewModel
import com.ian.recyclerviewhelper.helper.setUpHorizontal
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_home.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
class HomeFragment : Fragment(), HomeView {

    private var actualView: View? = null
    private val allFoodVM: AllFoodViewModel by viewModel()
    private var mHandler: Handler? = null
    private var pageSize: Int? = 0
    private var currentPage = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.withViewModel({ HomePresenter(allFoodVM) }) {
            attachView(this@HomeFragment, this@HomeFragment)
            onAttach()
            initAllData()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mHandler = Handler()
        val views = container?.inflates(R.layout.fragment_home)
        views?.let { initView(it) }
        return views
    }


    override fun initView(view: View) {
        this.actualView = view
        actualView?.tvSeeAllCategoryFood?.setOnClickListener {
            context?.startActivity<DiscoverActivity>()
        }

        actualView?.lnIcon2?.setOnClickListener {
            context?.startActivity<DetailInformationActivity> {
                putExtra(Constant.ingredientDetail, Constant.ingredientDetail)
            }
        }
        actualView?.lnIcon3?.setOnClickListener {
            context?.startActivity<DetailInformationActivity> {
                putExtra(Constant.areaDetail, Constant.areaDetail)
            }
        }
    }

    override fun onFailedGetData(msg: String?) {
        actualView?.shimmerHome?.stopShimmer()
        actualView?.shimmerHome?.gone()
        logE(msg)
    }

    private var slideRunnable: Runnable = object : Runnable {
        override fun run() {
            if (currentPage == pageSize) {
                currentPage = 0
            }
            actualView?.vpBestFood?.setCurrentItem(currentPage++, true)
            mHandler?.postDelayed(this, delayMillis)
        }
    }

    override fun onGetAllFood(data: List<LocalAllFoodData>?) {
        pageSize = data?.size
        actualView?.vpBestFood?.adapter = data?.let {
            context?.let { nonNullContext ->
                SliderItemAdapter(
                        it,
                        nonNullContext
                )
            }
        }
        actualView?.indicator?.setViewPager(vpBestFood)
        if (mHandler != null) {
            mHandler?.removeCallbacks(slideRunnable)
        }
        mHandler?.postDelayed(slideRunnable, delayMillis)
    }

    override fun onGetAllFoodCategoryDetails(data: List<LocalAllFoodCategoryDetailData>?) {
        actualView?.shimmerHome?.stopShimmer()
        actualView?.shimmerHome?.gone()
        data?.let { nonNullData ->
            actualView?.rvFood?.setUpHorizontal(nonNullData, R.layout.item_home, {
                with(this) {
                    tvHomeFoodName.text = it.strCategory
                    ivHomeFood.loadWithGlide(it.strCategoryThumb)
                }
            }, {
                context?.startActivity<FilterActivity> {
                    putExtra(Constant.categoryType, this@setUpHorizontal.strCategory)
                }
            })
        }
    }


    override fun onGetRandomFood(data: DetailFood.Meal?) {
        ivRandomFood.loadWithGlide(data?.strMealThumb)
        lnIcon1.setOnClickListener {
            context?.startActivity<DetailFoodActivity> {
                putExtra(intentDetailKey, data?.idMeal)
            }
        }
    }

    override fun onFailGetAllFood() {
        actualView?.shimmerHome?.stopShimmer()
        actualView?.shimmerHome?.gone()
    }

    override fun onFailGetAllFoodCategoryDetails() {
        actualView?.shimmerHome?.stopShimmer()
        actualView?.shimmerHome?.gone()
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
        actualView?.shimmerHome?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        actualView?.shimmerHome?.startShimmer()
    }
}