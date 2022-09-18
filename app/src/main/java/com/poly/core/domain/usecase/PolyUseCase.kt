package com.poly.core.domain.usecase

import com.poly.core.data.Resource
import com.poly.core.data.source.remote.response.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PolyUseCase {
    fun serviceFileImage(request: Service.Request): Flow<Resource<Service.Response>>
    fun register(requestRegister: Auth.RequestRegister): Flow<Resource<Auth.Response>>
    fun login(requestLogin: Auth.RequestLogin): Flow<Resource<Auth.Response>>
    fun job(request: Job.Request): Flow<Resource<Job.Response>>
    fun job(id: String, request: Job.Request): Flow<Resource<Job.Response>>
    fun job(id: String): Flow<Resource<Job.Response>>
    fun job(): Flow<Resource<Job.ResponseAll>>
    fun business(request: Business.Request): Flow<Resource<Business.Response>>
    fun business(id: String, request: Business.Request): Flow<Resource<Business.Response>>
    fun business(id: String): Flow<Resource<Business.Response>>
    fun business(): Flow<Resource<Business.ResponseAll>>
    fun store(userId: String): Flow<Resource<Store.Response>>
    fun store(request: Store.Request): Flow<Resource<Store.ResponseData>>
    fun store(id: String, request: Store.Request): Flow<Resource<Store.ResponseData>>
    fun moment(request: Moment.Request): Flow<Resource<Moment.Response>>
    fun moment(id: String, request: Moment.Request): Flow<Resource<Moment.Response>>
    fun moment(id: String): Flow<Resource<Moment.Response>>
    fun moment(): Flow<Resource<Moment.ResponseAll>>
    fun postMultiple(
        userId: RequestBody,
        location: RequestBody,
        description: RequestBody,
        files: List<MultipartBody.Part>
    ): Flow<Resource<Moment.Response>>
}