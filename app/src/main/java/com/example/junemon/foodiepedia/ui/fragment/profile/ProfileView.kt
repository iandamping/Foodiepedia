package com.example.junemon.foodiepedia.ui.fragment.profile

import com.example.junemon.foodiepedia.base.BaseFragmentView
import com.example.junemon.foodiepedia.model.UserProfileData

/**
 *
Created by Ian Damping on 10/06/2019.
Github = https://github.com/iandamping
 */
interface ProfileView : BaseFragmentView {
    fun onSuccessGetProfileData(data: UserProfileData)
}