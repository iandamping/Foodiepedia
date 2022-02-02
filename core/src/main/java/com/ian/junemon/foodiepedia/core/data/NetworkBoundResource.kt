package com.ian.junemon.foodiepedia.core.data

import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<RepositoryData<ResultType>> = flow {
        val dbSource = loadFromDB().first()


        if (shouldFetch(dbSource)) {
            when (val apiResponse = createCall().first()) {

                is DataSourceHelper.DataSourceValue -> {
                    saveCallResult(apiResponse.data)

                    emitAll(loadFromDB().map {
                        RepositoryData.Success(
                            it
                        )
                    })
                }

                is DataSourceHelper.DataSourceError -> {
                    onFetchFailed()
                    emit(
                        RepositoryData.Error(
                            apiResponse.exception.localizedMessage
                                ?: "Application encounter unknown error"
                        )
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map {
                RepositoryData.Success(
                    it
                )
            })
        }

    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<DataSourceHelper<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<RepositoryData<ResultType>> = result
}