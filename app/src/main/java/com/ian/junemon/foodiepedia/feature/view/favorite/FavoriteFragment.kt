package com.ian.junemon.foodiepedia.feature.view.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.util.mapListFavToCachePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentFavoriteBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.NavigationViewModel
import com.ian.junemon.foodiepedia.util.getDrawables
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.util.observeEvent
import com.ian.junemon.foodiepedia.util.verticalRecyclerviewInitializer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by Ian Damping on 11,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FavoriteFragment : BaseFragmentViewBinding<FragmentFavoriteBinding>(),FavoriteAdapter.FavoriteAdapterListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: FoodViewModel

    @Inject
    lateinit var loadImageHelper: LoadImageHelper
    @Inject
    lateinit var favAdapter: FavoriteAdapter
    private val navigationVm: NavigationViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding
        get() = FragmentFavoriteBinding::inflate

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)
        binding.rvFavorite.apply {
            verticalRecyclerviewInitializer()
            adapter = favAdapter
        }
        with(loadImageHelper){
            binding.ivNoData.loadWithGlide(getDrawables(R.drawable.no_data))
        }
    }

    override fun activityCreated() {
        getFavoriteFood()
        obvserveNavigation()
    }

    private fun getFavoriteFood() {
        observe(foodVm.getSavedDetailCache()) { cacheValue ->
            when (cacheValue) {
                is Results.Success -> {
                    binding.rvFavorite.visibility = View.VISIBLE
                    binding.ivNoData.visibility = View.GONE
                    with(favAdapter) {
                        submitList(cacheValue.data.mapListFavToCachePresentation())
                        // Force a redraw
                        this.notifyDataSetChanged()
                    }
                }
                is Results.Error -> {
                    binding.rvFavorite.visibility = View.GONE
                    binding.ivNoData.visibility = View.VISIBLE
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

    override fun onClicked(data: FoodCachePresentation) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(data)
        navigationVm.setNavigationDirection(action)
    }
}