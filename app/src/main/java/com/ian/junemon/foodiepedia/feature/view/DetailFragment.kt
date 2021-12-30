package com.ian.junemon.foodiepedia.feature.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.base.BaseFragmentDataBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.core.util.mapToDetailDatabasePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentDetailBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.observe
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DetailFragment : BaseFragmentDataBinding<FragmentDetailBinding>() {
    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: FoodViewModel

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    @Inject
    lateinit var intentHelper: IntentUtilHelper

    @Inject
    lateinit var gson: Gson

    private var idForDeleteItem: Int? = null
    private var isFavorite: Boolean = false
    private val passedData by lazy { args.detailValue }

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)

        binding.apply {
            initView()
            detailFood = passedData
        }
    }

    override fun activityCreated() {
        consumeBookmarkData()
        observeUiState()
    }

    private fun FragmentDetailBinding.initView() {
        with(loadImageHelper) {
            ivFoodDetail.loadWithGlide(passedData.foodImage)
        }

        clicks(btnBookmark) {
            if (isFavorite) {
                if (idForDeleteItem != null) foodVm.deleteSelectedId(idForDeleteItem!!)
            } else {
                foodVm.setCacheDetailFood(passedData.mapToDetailDatabasePresentation())
            }
        }

        clicks(btnBack) {
            navigateUp()
        }

        clicks(btnShare) {
            consumeSuspend {
                setDialogShow(false)
                intentHelper.intentShareImageAndText(
                    tittle = passedData.foodName,
                    imageUrl = passedData.foodImage,
                    message = passedData.foodCategory
                ) {
                    setDialogShow(true)
                    sharedImageIntent(it)
                }

            }
        }


        appbarDetailLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            var isShow = true
            var scrollRange: Int = -1

            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + i == 0) {
                collapsingToolbar.title = passedData.foodName
                // tvDetailTittles.visibility = View.GONE
                // isShow = true
            } else if (isShow) {
                collapsingToolbar.title = " "
                // tvDetailTittles.visibility = View.VISIBLE
                // isShow = false
            }
        })
    }

    private fun observeUiState() {
        foodVm.savedFood.asLiveData().observe(viewLifecycleOwner) {
            when {
                it.errorMessage.isNotEmpty() -> {
                    isFavorite = false
                    binding.bookmarkedState = isFavorite
                }
                it.data.isNotEmpty() -> {
                    val data = it.data.filter { result -> result.foodName == passedData.foodName }
                    if (data.isNotEmpty()) {
                        data.forEach { savedFood ->
                            if (savedFood.foodName == passedData.foodName) {
                                idForDeleteItem = savedFood.localFoodID
                                isFavorite = true
                                binding.bookmarkedState = isFavorite
                            }
                        }
                    } else {
                        isFavorite = false
                        binding.bookmarkedState = isFavorite
                    }
                }
            }
        }
    }

    private fun consumeBookmarkData() {
        foodVm.getSavedDetailCache()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate
}