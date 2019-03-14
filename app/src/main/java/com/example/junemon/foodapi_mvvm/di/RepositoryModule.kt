package com.example.junemon.foodapi_mvvm.di

import com.example.junemon.foodapi_mvvm.data.repo.AllFoodCategoryDetailRepo
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodRepo
import com.example.junemon.foodapi_mvvm.data.repo.DetailFoodRepo
import org.koin.dsl.module.module

object RepositoryModule {
    val allRepoModul = module {
        single { DetailFoodRepo(get()) }
        single { AllFoodRepo(get()) }
        single { AllFoodCategoryDetailRepo(get()) }
    }
}