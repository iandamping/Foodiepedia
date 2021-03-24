package com.ian.junemon.foodiepedia.combine

import com.ian.junemon.foodiepedia.dummy.DummyData
import com.ian.junemon.foodiepedia.FakeResults
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 23,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FakeCombine {

    fun getResult(): Flow<FakeResults<DummyData>>
}