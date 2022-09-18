package com.poly.core.data

import com.poly.core.data.source.local.LocalDataSource
import com.poly.core.data.source.remote.RemoteDataSource
import com.poly.core.data.source.remote.network.ApiResponse
import com.poly.core.data.source.remote.response.*
import com.poly.core.domain.repository.IPolyRepository
import com.poly.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PolyRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IPolyRepository {

    override fun serviceFileImage(request: Service.Request): Flow<Resource<Service.Response>> =
        object : NetworkResource<Service.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Service.Response>> {
                return remoteDataSource.serviceFileImage(request)
            }
        }.asFlow()

    override fun register(requestRegister: Auth.RequestRegister): Flow<Resource<Auth.Response>> =
        object : NetworkResource<Auth.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Auth.Response>> {
                return remoteDataSource.register(requestRegister)
            }
        }.asFlow()

    override fun login(requestLogin: Auth.RequestLogin): Flow<Resource<Auth.Response>> =
        object : NetworkResource<Auth.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Auth.Response>> {
                return remoteDataSource.login(requestLogin)
            }
        }.asFlow()

    override fun job(request: Job.Request): Flow<Resource<Job.Response>> =
        object : NetworkResource<Job.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Job.Response>> {
                return remoteDataSource.job(request)
            }
        }.asFlow()

    override fun job(id: String, request: Job.Request): Flow<Resource<Job.Response>> =
        object : NetworkResource<Job.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Job.Response>> {
                return remoteDataSource.job(id, request)
            }
        }.asFlow()

    override fun job(id: String): Flow<Resource<Job.Response>> =
        object : NetworkResource<Job.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Job.Response>> {
                return remoteDataSource.job(id)
            }
        }.asFlow()

    override fun job(): Flow<Resource<Job.ResponseAll>> =
        object : NetworkResource<Job.ResponseAll>() {
            override suspend fun createCall(): Flow<ApiResponse<Job.ResponseAll>> {
                return remoteDataSource.job()
            }
        }.asFlow()

    override fun business(request: Business.Request): Flow<Resource<Business.Response>> =
        object : NetworkResource<Business.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Business.Response>> {
                return remoteDataSource.business(request)
            }
        }.asFlow()

    override fun business(id: String, request: Business.Request): Flow<Resource<Business.Response>> =
        object : NetworkResource<Business.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Business.Response>> {
                return remoteDataSource.business(id, request)
            }
        }.asFlow()

    override fun business(id: String): Flow<Resource<Business.Response>> =
        object : NetworkResource<Business.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Business.Response>> {
                return remoteDataSource.business(id)
            }
        }.asFlow()

    override fun business(): Flow<Resource<Business.ResponseAll>> =
        object : NetworkResource<Business.ResponseAll>() {
            override suspend fun createCall(): Flow<ApiResponse<Business.ResponseAll>> {
                return remoteDataSource.business()
            }
        }.asFlow()

    override fun store(userId: String): Flow<Resource<Store.Response>> =
        object : NetworkResource<Store.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Store.Response>> {
                return remoteDataSource.store(userId)
            }
        }.asFlow()

    override fun store(request: Store.Request): Flow<Resource<Store.ResponseData>> =
        object : NetworkResource<Store.ResponseData>() {
            override suspend fun createCall(): Flow<ApiResponse<Store.ResponseData>> {
                return remoteDataSource.store(request)
            }
        }.asFlow()

    override fun store(id: String, request: Store.Request): Flow<Resource<Store.ResponseData>> =
        object : NetworkResource<Store.ResponseData>() {
            override suspend fun createCall(): Flow<ApiResponse<Store.ResponseData>> {
                return remoteDataSource.store(id, request)
            }
        }.asFlow()

    override fun moment(request: Moment.Request): Flow<Resource<Moment.Response>> =
        object : NetworkResource<Moment.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Moment.Response>> {
                return remoteDataSource.moment(request)
            }
        }.asFlow()

    override fun moment(id: String, request: Moment.Request): Flow<Resource<Moment.Response>> =
        object : NetworkResource<Moment.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Moment.Response>> {
                return remoteDataSource.moment(id, request)
            }
        }.asFlow()

    override fun moment(id: String): Flow<Resource<Moment.Response>> =
        object : NetworkResource<Moment.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Moment.Response>> {
                return remoteDataSource.moment(id)
            }
        }.asFlow()

    override fun moment(): Flow<Resource<Moment.ResponseAll>> =
        object : NetworkResource<Moment.ResponseAll>() {
            override suspend fun createCall(): Flow<ApiResponse<Moment.ResponseAll>> {
                return remoteDataSource.moment()
            }
        }.asFlow()

    override fun postMultiple(
        userId: RequestBody,
        location: RequestBody,
        description: RequestBody,
        files: List<MultipartBody.Part>
    ): Flow<Resource<Moment.Response>> =
        object : NetworkResource<Moment.Response>() {
            override suspend fun createCall(): Flow<ApiResponse<Moment.Response>> {
                return remoteDataSource.postMultiple(userId, location, description, files)
            }
        }.asFlow()
}