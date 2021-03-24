package com.ian.junemon.foodiepedia.flow1

import com.ian.junemon.foodiepedia.dummy.DummyData
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 23,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FakeFlowEmiter {

    fun getResult(): Flow<DummyData>
}