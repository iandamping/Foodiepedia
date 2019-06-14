package com.ian.junemon.foodiepedia.ui.fragment.profile

import com.ian.junemon.foodiepedia.base.BaseFragmentView
import com.ian.junemon.foodiepedia.model.UserProfileData

/**
 *
Created by Ian Damping on 10/06/2019.
Github = https://github.com/iandamping
 */
interface ProfileView : BaseFragmentView {
    fun onSuccessGetProfileData(data: UserProfileData)
}