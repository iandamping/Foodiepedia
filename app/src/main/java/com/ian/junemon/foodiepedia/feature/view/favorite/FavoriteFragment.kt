package com.ian.junemon.foodiepedia.feature.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.util.mapListFavToCachePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentFavoriteBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.util.observeEvent
import com.ian.junemon.foodiepedia.util.verticalRecyclerviewInitializer
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
    lateinit var favAdapter: FavoriteAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding
        get() = FragmentFavoriteBinding::inflate

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)
        binding.rvFavorite.apply {
            verticalRecyclerviewInitializer()
            adapter = favAdapter
        }
    }

    override fun activityCreated() {
        getFavoriteFood()
        observeViewEffect()
        obvserveNavigation()
    }

    private fun getFavoriteFood() {
        observe(foodVm.getSavedDetailCache()) { cacheValue ->
            when (cacheValue) {
                is Results.Success -> {
                    with(favAdapter) {
                        submitList(cacheValue.data.mapListFavToCachePresentation())
                        // Force a redraw
                        this.notifyDataSetChanged()
                    }
                }
                is Results.Error -> {
                    foodVm.setupSnackbarMessage(cacheValue.exception.message)
                }
            }
        }
    }

    private fun observeViewEffect() {
        observe(foodVm.snackbar) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                foodVm.onSnackbarShown()
            }

        }
    }

    private fun obvserveNavigation() {
        observeEvent(foodVm.navigateEvent) {
            navigate(it)
        }
    }

    override fun onClicked(data: FoodCachePresentation) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(data)
        foodVm.setNavigate(action)
    }
}