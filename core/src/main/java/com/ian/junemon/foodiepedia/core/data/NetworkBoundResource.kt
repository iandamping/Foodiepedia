package com.ian.junemon.foodiepedia.core.data

import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Results<ResultType>> = flow {
        emit(Results.Loading)
        val dbSource = loadFromDB().first()


        if (shouldFetch(dbSource)) {
            emit(Results.Loading)

            when (val apiResponse = createCall().first()) {

                is DataSourceHelper.DataSourceValue -> {
                    saveCallResult(apiResponse.data)

                    emitAll(loadFromDB().map {
                        Results.Success(
                            it
                        )
                    })
                }

                is DataSourceHelper.DataSourceError -> {
                    onFetchFailed()
                    emit(
                        Results.Error(
                            apiResponse.exception
                        )
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map {
                Results.Success(
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

    fun asFlow(): Flow<Results<ResultType>> = result
}