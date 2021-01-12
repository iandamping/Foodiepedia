package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCaseImpl
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCaseImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class DomainModule {

    @Binds
    abstract fun bindFoodUseCase(foodUseCaseImpl: FoodUseCaseImpl): FoodUseCase

    @Binds
    abstract fun bindProfileUseCase(profileUseCaseImpl: ProfileUseCaseImpl): ProfileUseCase
}