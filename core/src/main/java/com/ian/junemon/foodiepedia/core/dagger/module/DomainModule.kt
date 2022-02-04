package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCaseImpl
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindFoodUseCase(foodUseCaseImpl: FoodUseCaseImpl): FoodUseCase

    @Binds
    fun bindProfileUseCase(profileUseCaseImpl: ProfileUseCaseImpl): ProfileUseCase
}