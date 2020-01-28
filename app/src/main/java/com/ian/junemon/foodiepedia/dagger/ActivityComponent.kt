package com.ian.junemon.foodiepedia.dagger

import com.ian.junemon.foodiepedia.core.dagger.scope.PerActivities
import dagger.Component

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@PerActivities
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {

    @Component.Factory
    interface Factory {
        fun appComponent(appComponent: AppComponent): ActivityComponent
    }
}