package com.example.junemon.foodapi_mvvm.di

import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodCategoryViewModel
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodListDataViewModel
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel
import com.example.junemon.foodapi_mvvm.data.viewmodel.DetailFoodViewModel
import org.koin.dsl.module.module

object ViewModelModule {
    val allViewmodelModule = module {
        single { DetailFoodViewModel(get()) }
        single { AllFoodViewModel(get()) }
        single { AllFoodCategoryViewModel(get()) }
        single { AllFoodListDataViewModel(get()) }
    }
}