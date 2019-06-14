package com.ian.junemon.foodiepedia.di

import com.ian.junemon.foodiepedia.data.viewmodel.*
import org.koin.dsl.module.module

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

object ViewModelModule {
    val allViewmodelModule = module {
        factory { DetailFoodViewModel(get()) }
        factory { AllFoodViewModel(get(), get(), get()) }
        factory { AllFoodCategoryViewModel(get()) }
        factory { AllFoodListDataViewModel(get()) }
        factory { FilterFoodViewModel(get()) }
        factory { LocalDataViewModel(get()) }
    }
}