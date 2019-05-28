package com.example.junemon.foodapi_mvvm.di

import com.example.junemon.foodapi_mvvm.data.viewmodel.*
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
    }
}