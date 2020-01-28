package com.ian.junemon.foodiepedia.core.dagger.component

import com.ian.junemon.foodiepedia.core.data.di.CoroutineModule
import com.ian.junemon.foodiepedia.core.presentation.di.PresentationModule
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.core.remote.di.RemoteHelperModule
import com.ian.junemon.foodiepedia.core.remote.di.RemoteModule
import com.ian.junemon.foodiepedia.core.remote.util.FoodRemoteHelper
import dagger.Component

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
/*
@Component(
    modules = [RemoteModule::class, PresentationModule::class, RemoteHelperModule::class, CoroutineModule::class]
)
interface CoreUtilComponent {

    val provideLoadImageHelper: LoadImageHelper

    val provideIntentUtil: IntentUtilHelper

    val provideImageUtil: ImageUtilHelper

    val providePermissionHelperUtil: PermissionHelper

    val provideRecyclerViewHelper: RecyclerHelper

    val provideViewHelper: ViewHelper

    val provideFoodRemoteHelper: FoodRemoteHelper
}*/
