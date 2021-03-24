package com.ian.junemon.foodiepedia.flow1

import com.ian.junemon.foodiepedia.dummy.DummyData
import com.ian.junemon.foodiepedia.dummy.DummyObject.DUMMY_0_10
import com.ian.junemon.foodiepedia.dummy.DummyObject.DUMMY_10_19
import com.ian.junemon.foodiepedia.dummy.DummyObject.DUMMY_20_30
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFlowEmiterImpl(private val age: Int) : FakeFlowEmiter {

    override fun getResult(): Flow<DummyData> {
        return flow {
            when {
                age == 0 -> emit(DUMMY_0_10)
                age <= 20 -> emit(DUMMY_10_19)
                age > 20 -> emit(DUMMY_20_30)
                else -> emit(DUMMY_20_30)
            }
        }
    }
}