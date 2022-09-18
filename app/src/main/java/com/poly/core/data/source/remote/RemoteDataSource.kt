package com.poly.core.data.source.remote

import com.poly.core.data.source.remote.network.ApiResponse
import com.poly.core.data.source.remote.network.ApiService
import com.poly.core.data.source.remote.response.*
import com.poly.utils.ErrorUtils.getErrorThrowableMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RemoteDataSource(private val apiService: ApiService) {

    companion object {
        private const val TAG = "RemoteDataSource"
    }

    suspend fun serviceFileImage(request: Service.Request): Flow<ApiResponse<Service.Response>> {
        return flow {
            try {
                val response = apiService.serviceFileImage(request)
                if (response.status)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun register(requestRegister: Auth.RequestRegister): Flow<ApiResponse<Auth.Response>> {
        return flow {
            try {
                val response = apiService.register(requestRegister)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun login(requestLogin: Auth.RequestLogin): Flow<ApiResponse<Auth.Response>> {
        return flow {
            try {
                val response = apiService.login(requestLogin)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun job(request: Job.Request): Flow<ApiResponse<Job.Response>> {
        return flow {
            try {
                val response = apiService.job(request)
                if (response.statusCode == 201)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun job(id: String, request: Job.Request): Flow<ApiResponse<Job.Response>> {
        return flow {
            try {
                val response = apiService.job(id, request)
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun job(id: String): Flow<ApiResponse<Job.Response>> {
        return flow {
            try {
                val response = apiService.job(id)
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun job(): Flow<ApiResponse<Job.ResponseAll>> {
        return flow {
            try {
                val response = apiService.job()
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun business(request: Business.Request): Flow<ApiResponse<Business.Response>> {
        return flow {
            try {
                val response = apiService.business(request)
                if (response.statusCode == 201)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun business(
        id: String,
        request: Business.Request
    ): Flow<ApiResponse<Business.Response>> {
        return flow {
            try {
                val response = apiService.business(id, request)
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun business(id: String): Flow<ApiResponse<Business.Response>> {
        return flow {
            try {
                val response = apiService.business(id)
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun business(): Flow<ApiResponse<Business.ResponseAll>> {
        return flow {
            try {
                val response = apiService.business()
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun store(userId: String): Flow<ApiResponse<Store.Response>> {
        return flow {
            try {
                val response = apiService.store(userId)
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun store(request: Store.Request): Flow<ApiResponse<Store.ResponseData>> {
        return flow {
            try {
                val response = apiService.store(request)
                if (response.statusCode == 201)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun store(id: String, request: Store.Request): Flow<ApiResponse<Store.ResponseData>> {
        return flow {
            try {
                val response = apiService.store(id, request)
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun moment(request: Moment.Request): Flow<ApiResponse<Moment.Response>> {
        return flow {
            try {
                val response = apiService.moment(request)
                if (response.statusCode == 201)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun moment(id: String, request: Moment.Request): Flow<ApiResponse<Moment.Response>> {
        return flow {
            try {
                val response = apiService.moment(id, request)
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun moment(id: String): Flow<ApiResponse<Moment.Response>> {
        return flow {
            try {
                val response = apiService.moment(id)
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun moment(): Flow<ApiResponse<Moment.ResponseAll>> {
        return flow {
            try {
                val response = apiService.moment()
                if (response.statusCode == 200)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postMultiple(
        userId: RequestBody,
        location: RequestBody,
        description: RequestBody,
        files: List<MultipartBody.Part>
    ): Flow<ApiResponse<Moment.Response>> {
        return flow {
            try {
                val response = apiService.postMultiple(userId, location, description, files)
                if (response.statusCode == 201)
                    emit(ApiResponse.Success(response))
                else
                    emit(ApiResponse.Error(response.message))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResponse.Error(getErrorThrowableMsg(e)))
            }
        }.flowOn(Dispatchers.IO)
    }
}