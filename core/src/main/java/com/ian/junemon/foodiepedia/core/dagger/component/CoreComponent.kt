package com.ian.junemon.foodiepedia.core.dagger.component

import android.app.Application
import com.ian.junemon.foodiepedia.core.cache.di.DatabaseModule
import com.ian.junemon.foodiepedia.core.presentation.di.PresentationModule
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ian Damping on 16,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

@Component(
    modules = [
        DatabaseModule::class],dependencies = [CoreUtilComponent::class]
)
@Singleton
interface CoreComponent {



    @Component.Factory
    interface Factory {
        fun injectApplication(@BindsInstance application: Application,coreUtilComponent: CoreUtilComponent): CoreComponent
    }
}