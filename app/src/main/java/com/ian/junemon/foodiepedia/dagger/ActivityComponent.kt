package com.ian.junemon.foodiepedia.dagger

import android.content.Context
import com.ian.junemon.foodiepedia.core.cache.util.PreferenceHelper
import com.ian.junemon.foodiepedia.core.dagger.scope.PerActivities
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.ian.junemon.foodiepedia.core.presentation.di.PresentationModule
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
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

    val provideRecyclerHelper: RecyclerHelper

    val provideLoadImageHelper: LoadImageHelper

    val providePermissionHelper: PermissionHelper

    val provideImageUtilHelper: ImageUtilHelper

    val provideIntentUtilHelper: IntentUtilHelper

    val provideFoodRepository: FoodRepository

    val provideProfileRepository: ProfileRepository

    val provideContext: Context

    @Component.Factory
    interface Factory {
        fun appComponent(appComponent: AppComponent): ActivityComponent
    }
}

interface ActivityComponentProvider {

    fun provideActivityComponent(): ActivityComponent
}