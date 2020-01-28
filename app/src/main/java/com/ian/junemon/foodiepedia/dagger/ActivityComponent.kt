package com.ian.junemon.foodiepedia.dagger

import com.ian.junemon.foodiepedia.core.dagger.scope.PerActivities
import com.ian.junemon.foodiepedia.core.presentation.di.PresentationModule
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.ui.MainActivity
import com.ian.junemon.foodiepedia.ui.SplashActivity
import dagger.Component

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@PerActivities
@Component(modules = [PresentationModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: MainActivity)

    val provideViewHelper: ViewHelper

    val providePermissionHelper: PermissionHelper

    val provideImageUtilHelper: ImageUtilHelper

    @Component.Factory
    interface Factory {
        fun appComponent(appComponent: AppComponent): ActivityComponent
    }
}