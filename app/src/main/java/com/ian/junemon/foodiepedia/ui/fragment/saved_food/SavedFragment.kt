package com.ian.junemon.foodiepedia.ui.fragment.saved_food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.data.local_data.LocalFoodData
import com.ian.junemon.foodiepedia.data.viewmodel.LocalDataViewModel
import com.ian.junemon.foodiepedia.ui.activity.detail.DetailFoodActivity
import com.ian.junemon.foodiepedia.util.Constant.intentDetailKey
import com.ian.junemon.foodiepedia.util.alertHelperDelete
import com.ian.junemon.foodiepedia.util.initPresenter
import com.ian.app.helper.util.*
import com.ian.recyclerviewhelper.helper.setUpVertical
import kotlinx.android.synthetic.main.fragment_saved_food.view.*
import kotlinx.android.synthetic.main.item_saved_food.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class SavedFragment : Fragment(), SavedView {
    private var actualView: View? = null
    private val vm: LocalDataViewModel by viewModel()
    private lateinit var presenter: SavedPresenter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = initPresenter { SavedPresenter(vm) }.apply {
            attachView(this@SavedFragment, this@SavedFragment)
            initLocalData()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_saved_food)
        views?.let { initView(it) }
        return views
    }

    override fun onSuccesGetLocalData(data: List<LocalFoodData>) {
        actualView?.shimmerSavedFood?.stopShimmer()
        actualView?.shimmerSavedFood?.gone()
        actualView?.rvSavedFood?.setUpVertical(data, R.layout.item_saved_food, {
            with(this) {
                ivSavedFood.loadWithGlide(it.strMealThumb)
                tvSavedFoodTittle.text = it.strMeal
                tvSavedFoodCategory.text = it.strCategory
                ivDeleteFood.setOnClickListener { v ->
                    context?.alertHelperDelete(it.strMealThumb) {
                        presenter.deleteSelectedFood(it.localID)
                    }
                }
            }
        }, {
            context?.startActivity<DetailFoodActivity> {
                putExtra(intentDetailKey, idMeal)
            }
        })
    }

    override fun initView(view: View) {
        this.actualView = view
    }

    override fun onFailedGetData(msg: String?) {
        actualView?.tvSavedFoodEmpty?.visible()
        actualView?.shimmerSavedFood?.stopShimmer()
        actualView?.shimmerSavedFood?.gone()
    }

    override fun onPause() {
        super.onPause()
        actualView?.shimmerSavedFood?.stopShimmer()
    }

    override fun onResume() {
        super.onResume()
        actualView?.shimmerSavedFood?.startShimmer()
    }
}