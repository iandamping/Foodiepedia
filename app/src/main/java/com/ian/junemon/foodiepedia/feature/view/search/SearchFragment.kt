package com.ian.junemon.foodiepedia.feature.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.presentation.view.ViewHelper
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentSearchBinding
import com.ian.junemon.foodiepedia.feature.vm.SearchFoodViewModel
import com.ian.junemon.foodiepedia.util.gridRecyclerviewInitializer
import com.ian.junemon.foodiepedia.util.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ian Damping on 26,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class SearchFragment @Inject constructor(
    private val viewHelper: ViewHelper,
    private val factory: SearchAdapter.Factory
) : BaseFragmentViewBinding<FragmentSearchBinding>(),
    SearchAdapter.SearchAdapterListener {


    private val searchAdapter: SearchAdapter by lazy {
        factory.create(this)
    }


    private val foodVm: SearchFoodViewModel by viewModels()

    private var text: String? = null

    override fun viewCreated() {
        binding.initView()
        observeUiState()
        foodVm.filteredData.observe(viewLifecycleOwner) { savedQuery ->
            if (savedQuery.isNotEmpty()) {
                binding.searchViews.setQuery(savedQuery, false)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!text.isNullOrEmpty()) {
            foodVm.setQuery(text!!)
        }
    }

    override fun activityCreated() {
        observe(foodVm.snackbar) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                foodVm.onSnackbarShown()
            }
        }
    }


    private fun FragmentSearchBinding.initView() {
        rvSearchPlace.apply {
            gridRecyclerviewInitializer(2)
            adapter = searchAdapter
        }

        searchViews.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!searchViews.isIconified) {
                    searchViews.isIconified = true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                foodVm.searchFood(newText)
                text = newText
                return false

            }
        })
    }

    private fun observeUiState() {
        foodVm.searchFoodCache.asLiveData().observe(viewLifecycleOwner) {
            when {
                it.errorMessage.isNotEmpty() -> {
                    with(viewHelper) {
                        visible(binding.lnSearchFailed)
                        gone(binding.rvSearchPlace)
                    }
                    foodVm.setupSnackbarMessage(it.errorMessage)

                }
                it.data.isNotEmpty() -> {
                    searchAdapter.submitList(it.data.map { mapData ->
                        mapData.mapToCachePresentation()
                    })
                    with(viewHelper) {
                        gone(binding.lnSearchFailed)
                        visible(binding.rvSearchPlace)
                    }
                }
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun onClicked(data: FoodCachePresentation) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(data)
        navigate(action)
    }
}