package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.presentation.view.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface PresentationModule {

    @Binds
    fun bindsLoadImageHelper(loadImageHelper: LoadImageImpl): LoadImageHelper

    @Binds
    fun bindsPermissionHelperUtil(permissionUtil: PermissionUtilImpl): PermissionHelper

    @Binds
    fun bindsIntentUtil(intentUtilImpl: IntentUtilImpl): IntentUtilHelper

    @Binds
    fun bindsImageUtil(imageUtilImpl: ImageUtilImpl): ImageUtilHelper

    @Binds
    fun bindsViewHelper(viewHelper: ViewHelperImpl): ViewHelper
}