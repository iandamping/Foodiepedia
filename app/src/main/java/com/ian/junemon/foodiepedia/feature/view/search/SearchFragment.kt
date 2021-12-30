package com.ian.junemon.foodiepedia.feature.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentSearchBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.NavigationViewModel
import com.ian.junemon.foodiepedia.feature.vm.SearchFoodViewModel
import com.ian.junemon.foodiepedia.util.gridRecyclerviewInitializer
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.util.observe
import java.util.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 26,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFragment : BaseFragmentViewBinding<FragmentSearchBinding>(),
    SearchAdapter.SearchAdapterListener {
    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: SearchFoodViewModel

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)
        binding.initView()
        observeUiState()
    }

    override fun activityCreated() {
        /**Show a snackbar whenever the [snackbar] is updated a non-null value*/
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
                return false

            }
        })
    }

    private fun observeUiState() {
        foodVm.searchFoodCache.asLiveData().observe(viewLifecycleOwner) {
            when {
                it.errorMessage.isNotEmpty() -> {
                    with(viewHelper) {
                        binding.lnSearchFailed.visible()
                        binding.rvSearchPlace.gone()
                    }
                    foodVm.setupSnackbarMessage(it.errorMessage)

                }
                it.data.isNotEmpty() -> {
                    searchAdapter.submitList(it.data.map { mapData ->
                        mapData.mapToCachePresentation()
                    })
                    with(viewHelper) {
                        binding.lnSearchFailed.gone()
                        binding.rvSearchPlace.visible()
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