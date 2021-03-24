package com.ian.junemon.foodiepedia

import com.ian.junemon.foodiepedia.combine.FakeCombine
import com.ian.junemon.foodiepedia.combine.FakeCombineImpl
import com.ian.junemon.foodiepedia.dummy.DummyObject.DUMMY_10_19
import com.ian.junemon.foodiepedia.flow1.FakeFlowEmiter
import com.ian.junemon.foodiepedia.flow1.FakeFlowEmiterImpl
import com.ian.junemon.foodiepedia.flow2.FakeFlowEmitter2
import com.ian.junemon.foodiepedia.flow2.FakeFlowEmitterImpl2
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    //subject under test
    //FakeFlowEmiter

    // when(result){
    //     is FakeResults.Loading ->{}
    //     is FakeResults.Success ->{}
    //     is FakeResults.Error ->{}
    //     is FakeResults.Complete ->{}
    // }

    @Test
    fun takeFirstTest() = runBlockingTest {
        val fakeEmiter: FakeFlowEmiter = FakeFlowEmiterImpl(2)
        val fakeEmiter2: FakeFlowEmitter2 = FakeFlowEmitterImpl2(2)
        val combine:FakeCombine = FakeCombineImpl(fakeEmiter,fakeEmiter2)

        //take first item
        val value = combine.getResult().first()
        //take second item
        val value2 = combine.getResult().drop(1).first()
        // take third item
        val value3 = combine.getResult().drop(2).first()

        assertThat(
            value3, `is`(
                // FakeResults.Loading
                // FakeResults.Success(DUMMY_10_19)
                FakeResults.Complete
            )
        )

    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
