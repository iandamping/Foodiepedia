package com.ian.junemon.foodiepedia.flow2

import com.ian.junemon.foodiepedia.dummy.DummyData
import com.ian.junemon.foodiepedia.dummy.DummyObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Ian Damping on 23,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeFlowEmitterImpl2(private val age: Int) : FakeFlowEmitter2 {

    override fun getResult(): Flow<DummyData> {
        return flow {
            when {
                age == 0 -> emit(DummyObject.DUMMY_0_10)
                age <= 20 -> emit(DummyObject.DUMMY_10_19)
                age > 20 -> emit(DummyObject.DUMMY_20_30)
                else -> emit(DummyObject.DUMMY_20_30)
            }
        }
    }
}