package com.ian.junemon.foodiepedia.feature.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.databinding.FragmentSearchBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.util.observeEvent
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import com.ian.junemon.foodiepedia.feature.view.SearchFragmentDirections
import com.ian.junemon.foodiepedia.util.gridRecyclerviewInitializer
import kotlinx.android.synthetic.main.item_home.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 26,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFragment : BaseFragmentViewBinding<FragmentSearchBinding>(),SearchAdapter.SearchAdapterListener {
    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var recyclerViewHelper: RecyclerHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: FoodViewModel

    private lateinit var searchAdapter: SearchAdapter

    private var data: List<FoodCachePresentation> = mutableListOf()

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)
        searchAdapter = SearchAdapter(this,loadImageHelper)
        binding.initView()
    }


    override fun activityCreated() {
        initData()
        obvserveNavigation()

        /**Show a snackbar whenever the [snackbar] is updated a non-null value*/
        observe(foodVm.snackbar){ text ->
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
                    data = results.data.map { it.mapToCachePresentation() }
                    foodVm.setSearchItem(data = data.toMutableList())
                }
                is Results.Error -> {
                    foodVm.setupLoadingState(true)
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
                with(viewHelper) {
                    binding.lnSearchFailed.visible()
                    binding.rvSearchPlace.gone()
                }
            } else {
                with(viewHelper) {
                    binding.lnSearchFailed.gone()
                    binding.rvSearchPlace.visible()
                }
            }
        }
    }

    private fun initData() {
        observe(foodVm.searchItem){ data ->
            searchAdapter.submitList(data)
        }
    }


    private fun obvserveNavigation() {
        observeEvent(foodVm.navigateEvent) {
            navigate(it)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun onClicked(data: FoodCachePresentation) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(gson.toJson(this))
        foodVm.setNavigate(action)
    }
}