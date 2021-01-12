package com.ian.junemon.foodiepedia.dagger.module

import com.ian.junemon.foodiepedia.util.classes.ImageUtilImpl
import com.ian.junemon.foodiepedia.util.classes.IntentUtilImpl
import com.ian.junemon.foodiepedia.util.classes.LoadImageImpl
import com.ian.junemon.foodiepedia.util.classes.PermissionUtilImpl
import com.ian.junemon.foodiepedia.util.classes.RecyclerHelperImpl
import com.ian.junemon.foodiepedia.util.classes.ViewHelperImpl
import com.ian.junemon.foodiepedia.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class PresentationModule {

    @Binds
    abstract fun bindsIntentUtil(intentUtilImpl: IntentUtilImpl): IntentUtilHelper

    @Binds
    abstract fun bindsImageUtil(imageUtilImpl: ImageUtilImpl): ImageUtilHelper

    @Binds
    abstract fun bindsPermissionHelperUtil(permissionUtil: PermissionUtilImpl): PermissionHelper

    @Binds
    abstract fun bindsRecyclerViewHelper(recyclerHelper: RecyclerHelperImpl): RecyclerHelper

    @Binds
    abstract fun bindsLoadImageHelper(loadImageHelper: LoadImageImpl): LoadImageHelper

    @Binds
    abstract fun bindsViewHelper(viewHelper: ViewHelperImpl): ViewHelper
}