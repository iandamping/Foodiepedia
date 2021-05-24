package com.ian.junemon.foodiepedia.feature.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
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
import com.ian.junemon.foodiepedia.util.gridRecyclerviewInitializer
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.util.observeEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private lateinit var foodVm: FoodViewModel

    private val navigationVm: NavigationViewModel by activityViewModels()

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)
        binding.initView()
    }

    override fun activityCreated() {
        initData()
        obvserveNavigation()

        /**Show a snackbar whenever the [snackbar] is updated a non-null value*/
        observe(foodVm.snackbar) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                foodVm.onSnackbarShown()
            }
        }
        getCache()
    }

    private fun getCache() {
        foodVm.getCache().observe(viewLifecycleOwner, { results ->
            when (results) {
                is Results.Success -> {
                    searchAdapter.submitList(results.data.map { it.mapToCachePresentation() })
                }
                is Results.Error -> {
                    foodVm.setupSnackbarMessage(results.exception.message)
                }
            }

        })
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
                if (!newText.isNullOrEmpty()) {
                    searchFood(newText)
                }
                return false

            }
        })
    }

    @SuppressLint("DefaultLocale")
    private fun searchFood(s: String?) {
        foodVm.getCache().observe(viewLifecycleOwner,{ result ->
            when (result) {
                is Results.Success -> {
                    foodVm.setSearchItem(result.data.mapToCachePresentation().filter {
                        it.foodName?.toLowerCase()?.contains(s.toString())!!
                    })
                }
                is Results.Error -> {
                    foodVm.setupSnackbarMessage(result.exception.message)
                }
            }
        })
    }

    private fun initData() {
        observe(foodVm.searchItem) { data ->
            if (data.isEmpty()){
                with(viewHelper) {
                    binding.lnSearchFailed.visible()
                    binding.rvSearchPlace.gone()
                }
            }else {
                with(searchAdapter) {
                    submitList(data)
                    notifyDataSetChanged()
                }
                with(viewHelper) {
                    binding.lnSearchFailed.gone()
                    binding.rvSearchPlace.visible()
                }
            }
        }
    }

    private fun obvserveNavigation() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            navigationVm.navigationFlow.onEach {
                navigate(it)
            }.launchIn(this)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun onClicked(data: FoodCachePresentation) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(data)
        navigationVm.setNavigationDirection(action)
    }
}