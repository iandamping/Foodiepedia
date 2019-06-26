package com.ian.junemon.foodiepedia.di

import com.ian.junemon.foodiepedia.data.viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

val allViewmodelModule = module {
    viewModel { DetailFoodViewModel(get()) }
    viewModel { AllFoodViewModel(get(), get(), get(), get()) }
    viewModel { AllFoodCategoryViewModel(get(), get()) }
    viewModel { AllFoodListDataViewModel(get(), get()) }
    viewModel { FilterFoodViewModel(get()) }
    viewModel { LocalDataViewModel(get()) }
}
