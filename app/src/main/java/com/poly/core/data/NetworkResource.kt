package com.poly.core.data

import com.poly.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkResource<ResultType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data))
            }
            is ApiResponse.Empty -> {
                onFetchFailed()
                emit(Resource.Error("Empty Data"))
            }
            is ApiResponse.Error -> {
                onFetchFailed()
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun createCall(): Flow<ApiResponse<ResultType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}