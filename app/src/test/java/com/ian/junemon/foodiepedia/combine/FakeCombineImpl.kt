package com.ian.junemon.foodiepedia.combine

import com.ian.junemon.foodiepedia.dummy.DummyData
import com.ian.junemon.foodiepedia.FakeResults
import com.ian.junemon.foodiepedia.flow1.FakeFlowEmiter
import com.ian.junemon.foodiepedia.flow2.FakeFlowEmitter2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Created by Ian Damping on 23,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeCombineImpl(private val flow1: FakeFlowEmiter, private val flow2: FakeFlowEmitter2) :
    FakeCombine {

    override fun getResult(): Flow<FakeResults<DummyData>> {
        return combine(
            flow = flow1.getResult(),
            flow2 = flow2.getResult()
        ) { a, b ->
            if (a.copy() == b.copy()){
                FakeResults.Success(a.copy())
            } else{
                FakeResults.Error("ga")
            }
        }.onStart {
            emit(FakeResults.Loading)
        }.onCompletion {
            emit(FakeResults.Complete)
        }
    }
}