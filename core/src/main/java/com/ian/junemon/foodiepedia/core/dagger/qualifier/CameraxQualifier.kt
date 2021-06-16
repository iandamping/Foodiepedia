package com.ian.junemon.foodiepedia.core.dagger.qualifier

import javax.inject.Qualifier

/**
 * Created by Ian Damping on 29,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LensFacingBack

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LensFacingFront

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CameraxOutputDirectory

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CameraxPhotoFile

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CameraxOutputOptions

