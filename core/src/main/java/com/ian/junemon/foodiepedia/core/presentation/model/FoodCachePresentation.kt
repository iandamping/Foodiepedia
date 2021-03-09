package com.ian.junemon.foodiepedia.core.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Parcelize
data class FoodCachePresentation(
    val foodId: Int?,
    val foodName: String?,
    val foodCategory: String?,
    val foodArea: String?,
    val foodImage: String?,
    val foodContributor: String?,
    val foodDescription: String?
):Parcelable