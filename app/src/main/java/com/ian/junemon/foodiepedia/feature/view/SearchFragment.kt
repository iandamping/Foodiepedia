package com.ian.junemon.foodiepedia.feature.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.databinding.FragmentSearchBinding
import com.ian.junemon.foodiepedia.feature.util.EventObserver
import com.ian.junemon.foodiepedia.feature.util.FoodConstant
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.junemon.model.Results
import com.junemon.model.data.dto.mapToCachePresentation
import com.junemon.model.presentation.FoodCachePresentation
import kotlinx.android.synthetic.main.item_home.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 26,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFragment : BaseFragment() {
    @Inject
    lateinit var gson :Gson
    @Inject
    lateinit var loadImageHelper: LoadImageHelper
    @Inject
    lateinit var viewHelper: ViewHelper
    @Inject
    lateinit var recyclerViewHelper: RecyclerHelper
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: FoodViewModel

    private var data: List<FoodCachePresentation> = mutableListOf()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        foodVm = viewModelProvider(viewModelFactory)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()
    }

    override fun destroyView() {
        _binding = null

    }

    override fun activityCreated() {
        initData()
        setupNavigation()

        foodVm.getCache().observe(viewLifecycleOwner, { result ->
            data = result.map { it.mapToCachePresentation() }
            foodVm.setSearchItem(data = data.toMutableList())
        })
    }

    private fun FragmentSearchBinding.initView() {
        searchViews.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!searchViews.isIconified) {
                    searchViews.isIconified = true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchPlace(newText)
                return false
            }
        })
    }

    @SuppressLint("DefaultLocale")
    private fun searchPlace(s: String?) {
        ilegallStateCatching {
            val tempListData: MutableList<FoodCachePresentation> = mutableListOf()
            check(data.isNotEmpty())
            data.forEach {
                if (s?.toLowerCase()?.let { it1 -> it.foodName?.toLowerCase()?.contains(it1) }!!) {
                    tempListData.add(it)
                }
            }
            foodVm.setSearchItem(tempListData)
            if (tempListData.isEmpty()) {
                viewHelper.run {
                    binding.lnSearchFailed.visible()
                    binding.rvSearchPlace.gone()
                }
            } else {
                viewHelper.run {
                    binding.lnSearchFailed.gone()
                    binding.rvSearchPlace.visible()
                }
            }
        }
    }

    private fun initData() {
        foodVm.searchItem.observe(viewLifecycleOwner, {
            recyclerViewHelper.run {
                binding.rvSearchPlace.setUpVerticalGridAdapter(
                    items = it,
                    gridSize = 2,
                    diffUtil = FoodConstant.foodPresentationRvCallback,
                    layoutResId = R.layout.item_home,
                    bindHolder = {
                        with(this) {
                            loadImageHelper.run { ivFoodImage.loadWithGlide(it?.foodImage) }
                            tvFoodContributor.text = it?.foodContributor
                        }
                    },
                    itemClick = {
                        foodVm.moveToDetailFragment(gson.toJson(this))
                    })
            }
        })
    }

    private fun setupNavigation() {
        foodVm.moveToDetailFragmentEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToDetailFoodFragment(it)
        })
    }

    private fun navigateToDetailFoodFragment(foodValue: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(foodValue)
        navigate(action)
    }


}