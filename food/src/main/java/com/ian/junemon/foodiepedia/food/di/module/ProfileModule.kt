package com.ian.junemon.foodiepedia.food.di.module

import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.presentation.di.factory.ViewModelKey
import com.ian.junemon.foodiepedia.food.vm.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class ProfileModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel
}