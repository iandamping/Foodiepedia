package com.ian.junemon.foodiepedia.dagger.factory

import androidx.lifecycle.SavedStateHandle
import com.ian.junemon.foodiepedia.core.dagger.factory.ViewModelAssistedFactory
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.feature.vm.SearchFoodViewModel
import javax.inject.Inject


/**
 * Created by Ian Damping on 07,January,2022
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchFoodViewModelFactory @Inject constructor(
    private val repository: FoodUseCase
) :ViewModelAssistedFactory<SearchFoodViewModel>{
    override fun create(handle: SavedStateHandle): SearchFoodViewModel {
        return SearchFoodViewModel(handle, repository)
    }
}