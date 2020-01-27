package com.ian.junemon.foodiepedia.core.domain.di

import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object DomainModule {

    @Provides
    @JvmStatic
    fun provideFoodUseCase(repository: FoodRepository): FoodUseCase {
        return FoodUseCase(repository)
    }

    @Provides
    @JvmStatic
    fun provideProfileUseCase(repository: ProfileRepository): ProfileUseCase {
        return ProfileUseCase(repository)
    }
}