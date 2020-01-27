package com.ian.junemon.foodiepedia.core.dagger.component

import com.ian.junemon.foodiepedia.core.cache.di.DatabaseHelperModule
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.FoodDaoHelper
import com.ian.junemon.foodiepedia.core.presentation.di.PresentationModule
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.core.remote.di.RemoteHelperModule
import com.ian.junemon.foodiepedia.core.remote.util.RemoteHelper
import dagger.Component

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Component(modules = [PresentationModule::class, RemoteHelperModule::class, DatabaseHelperModule::class])
interface CoreUtilComponent {

    val provideLoadImageHelper: LoadImageHelper

    val provideIntentUtil: IntentUtilHelper

    val provideImageUtil: ImageUtilHelper

    val providePermissionHelperUtil: PermissionHelper

    val provideRecyclerViewHelper: RecyclerHelper

    val provideViewHelper: ViewHelper

    fun providePlacesDaoHelper(): FoodDaoHelper

    fun provideRemoteHelper(): RemoteHelper
}