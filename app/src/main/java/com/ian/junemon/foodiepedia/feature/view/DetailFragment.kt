package com.ian.junemon.foodiepedia.feature.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.base.BaseFragmentDataBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.core.util.mapToDetailDatabasePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentDetailBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DetailFragment : BaseFragmentDataBinding<FragmentDetailBinding>() {
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
    private val passedData by lazy {
        gson.fromJson(
            DetailFragmentArgs.fromBundle(requireArguments()).detailValue,
            FoodCachePresentation::class.java
        )
    }

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
            consumeBookmarkData()
        }
    }

    override fun activityCreated() {
    }

    private fun FragmentDetailBinding.initView() {
        ilegallStateCatching {
            checkNotNull(passedData)
            with(loadImageHelper) {
                ivFoodDetail.loadWithGlide(passedData.foodImage)
            }
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
        tvFoodName.text = passedData.foodName
        tvFoodCategory.text = passedData.foodCategory
        tvFoodContributor.text = passedData.foodContributor
        tvFoodArea.text = passedData.foodArea
        tvFoodDescription.text = passedData.foodDescription

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

    private fun FragmentDetailBinding.consumeBookmarkData() {
        observe(foodVm.getSavedDetailCache()){ cacheValue ->
            when (cacheValue) {
                is Results.Success -> {
                    val data = cacheValue.data.filter { it.foodName == passedData.foodName }
                    if (data.isNotEmpty()) {
                        data.forEach {
                            if (it.foodName == passedData.foodName) {
                                idForDeleteItem = it.localFoodID
                                isFavorite = true
                                bookmarkedState = isFavorite
                            }
                        }
                    } else {
                        isFavorite = false
                        bookmarkedState = isFavorite
                    }
                }
                is Results.Error -> {
                    isFavorite = false
                    bookmarkedState = isFavorite
                }
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate
}