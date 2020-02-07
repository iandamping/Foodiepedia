package com.ian.junemon.foodiepedia.core.testonly

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.junemon.model.domain.UserProfileDataModel
import org.jetbrains.annotations.TestOnly

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@TestOnly
@VisibleForTesting
internal class FakeProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    fun inflateLogin(): LiveData<UserProfileDataModel> = repository.inflateLogin()

    fun getUser(): LiveData<UserProfileDataModel> = repository.get()
}