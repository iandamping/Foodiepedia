package com.ian.junemon.foodiepedia.uploader.dagger.module

import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.dagger.factory.ViewModelKey
import com.ian.junemon.foodiepedia.uploader.dagger.scope.FragmentScoped
import com.ian.junemon.foodiepedia.uploader.feature.FragmentUpload
import com.ian.junemon.foodiepedia.uploader.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.uploader.feature.vm.UploadViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class UploadModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeFragmentUpload(): FragmentUpload

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(gameViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UploadViewModel::class)
    abstract fun bindUploadViewModel(gameViewModel: UploadViewModel): ViewModel
}