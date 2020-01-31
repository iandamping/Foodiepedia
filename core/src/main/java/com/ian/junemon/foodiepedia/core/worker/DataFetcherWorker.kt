package com.ian.junemon.foodiepedia.core.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.worker.creator.FetcherWorkerFactory
import com.junemon.model.WorkerResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Ian Damping on 30,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DataFetcherWorker (
    appContext: Context,
    params: WorkerParameters,
    private val foodRepository: FoodRepository
) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "DataFetcherWorker"
    }

    override suspend fun doWork(): Result {
        Timber.d("Called ?")
        val prefetchStatus = foodRepository.foodPrefetch()
        val completeAbleResult by lazy { CompletableDeferred<Result>() }
        prefetchStatus.collect { result ->
            when (result) {
                is WorkerResult.SuccessWork -> {
                    completeAbleResult.complete(Result.success())
                    Timber.d("Success Work")
                }
                is WorkerResult.ErrorWork -> {
                    completeAbleResult.complete(Result.failure())
                    Timber.e("Work fail to work because ${result.exception}")
                }
            }
        }
        return completeAbleResult.await()
    }

    class Factory @Inject constructor(
        private val foo: Provider<FoodRepository>
    ) : FetcherWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): CoroutineWorker {
            return DataFetcherWorker(
                appContext,
                params,
                foo.get()
            )
        }
    }
}