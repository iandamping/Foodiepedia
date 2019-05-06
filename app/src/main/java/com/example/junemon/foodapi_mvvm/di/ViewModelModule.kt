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
        single { DetailFoodViewModel(get()) }
        single { AllFoodViewModel(get()) }
        single { AllFoodCategoryViewModel(get()) }
        single { AllFoodListDataViewModel(get()) }
        single { FilterFoodViewModel(get()) }
    }
}