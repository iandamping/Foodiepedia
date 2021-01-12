package com.ian.junemon.foodiepedia.uploader.dagger.module

import com.ian.junemon.foodiepedia.uploader.util.classes.ImageUtilImpl
import com.ian.junemon.foodiepedia.uploader.util.classes.IntentUtilImpl
import com.ian.junemon.foodiepedia.uploader.util.classes.LoadImageImpl
import com.ian.junemon.foodiepedia.uploader.util.classes.PermissionUtilImpl
import com.ian.junemon.foodiepedia.uploader.util.classes.ViewHelperImpl
import com.ian.junemon.foodiepedia.uploader.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.uploader.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.uploader.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.uploader.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.uploader.util.interfaces.ViewHelper
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 12,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class UploadPresentationModule {

    @Binds
    abstract fun bindsIntentUtil(intentUtilImpl: IntentUtilImpl): IntentUtilHelper

    @Binds
    abstract fun bindsImageUtil(imageUtilImpl: ImageUtilImpl): ImageUtilHelper

    @Binds
    abstract fun bindsPermissionHelperUtil(permissionUtil: PermissionUtilImpl): PermissionHelper

    @Binds
    abstract fun bindsLoadImageHelper(loadImageHelper: LoadImageImpl): LoadImageHelper

    @Binds
    abstract fun bindsViewHelper(viewHelper: ViewHelperImpl): ViewHelper
}