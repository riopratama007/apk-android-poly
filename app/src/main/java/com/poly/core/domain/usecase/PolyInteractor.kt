package com.poly.core.domain.usecase

import com.poly.core.data.source.remote.response.*
import com.poly.core.domain.repository.IPolyRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PolyInteractor(private val iPolyRepository: IPolyRepository) : PolyUseCase {
    override fun serviceFileImage(request: Service.Request) = iPolyRepository.serviceFileImage(request)
    override fun register(requestRegister: Auth.RequestRegister) = iPolyRepository.register(requestRegister)
    override fun login(requestLogin: Auth.RequestLogin) = iPolyRepository.login(requestLogin)
    override fun job(request: Job.Request) = iPolyRepository.job(request)
    override fun job(id: String, request: Job.Request) = iPolyRepository.job(id, request)
    override fun job(id: String) = iPolyRepository.job(id)
    override fun job() = iPolyRepository.job()
    override fun business(request: Business.Request) = iPolyRepository.business(request)
    override fun business(id: String, request: Business.Request) = iPolyRepository.business(id, request)
    override fun business(id: String) = iPolyRepository.business(id)
    override fun business() = iPolyRepository.business()
    override fun store(userId: String) = iPolyRepository.store(userId)
    override fun store(request: Store.Request) = iPolyRepository.store(request)
    override fun store(id: String, request: Store.Request) = iPolyRepository.store(id, request)
    override fun moment(request: Moment.Request) = iPolyRepository.moment(request)
    override fun moment(id: String, request: Moment.Request) = iPolyRepository.moment(id, request)
    override fun moment(id: String) = iPolyRepository.moment(id)
    override fun moment() = iPolyRepository.moment()
    override fun postMultiple(
        userId: RequestBody,
        location: RequestBody,
        description: RequestBody,
        files: List<MultipartBody.Part>
    ) = iPolyRepository.postMultiple(userId, location, description, files)
}