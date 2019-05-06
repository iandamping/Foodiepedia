package com.example.junemon.foodapi_mvvm.di

import com.example.junemon.foodapi_mvvm.data.repo.*
import org.koin.dsl.module.module
/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

object RepositoryModule {
    val allRepoModul = module {
        single { DetailFoodRepo(get()) }
        single { AllFoodRepo(get()) }
        single { AllFoodCategoryDetailRepo(get()) }
        single { AllFoodListDataRepo(get()) }
        single { FilterFoodRepo(get()) }
    }
}