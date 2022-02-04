package com.ian.junemon.foodiepedia.feature.view.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.core.util.mapListFavToCachePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentFavoriteBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.util.getDrawables
import com.ian.junemon.foodiepedia.util.verticalRecyclerviewInitializer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ian Damping on 11,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class FavoriteFragment @Inject constructor(
    private val factory: FavoriteAdapter.Factory,
    private val loadImageHelper: LoadImageHelper
) : BaseFragmentViewBinding<FragmentFavoriteBinding>(),
    FavoriteAdapter.FavoriteAdapterListener {

    private val favAdapter: FavoriteAdapter by lazy {
        factory.create(this)
    }

    private val foodVm: FoodViewModel by viewModels()


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding
        get() = FragmentFavoriteBinding::inflate

    override fun viewCreated() {
        binding.rvFavorite.apply {
            verticalRecyclerviewInitializer()
            adapter = favAdapter
        }

        loadImageHelper.loadWithGlide(binding.ivNoData, getDrawables(R.drawable.no_data))

    }

    override fun activityCreated() {
        getFavoriteFood()
        observeUiState()
    }

    private fun observeUiState() {
        foodVm.savedFood.asLiveData().observe(viewLifecycleOwner) {
            when {
                it.data.isNotEmpty() -> {
                    binding.rvFavorite.visibility = View.VISIBLE
                    binding.ivNoData.visibility = View.GONE
                    favAdapter.submitList(it.data.mapListFavToCachePresentation())
                }
                it.errorMessage.isNotEmpty() -> {
                    binding.rvFavorite.visibility = View.GONE
                    binding.ivNoData.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getFavoriteFood() {
        foodVm.getSavedDetailCache()

    }


    override fun onClicked(data: FoodCachePresentation) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(data)
        navigate(action)
    }
}