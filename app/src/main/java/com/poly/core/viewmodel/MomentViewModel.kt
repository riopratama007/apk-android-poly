package com.poly.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.poly.core.data.source.remote.response.Moment
import com.poly.core.domain.usecase.PolyUseCase
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MomentViewModel(private val polyUseCase: PolyUseCase) : ViewModel() {
    fun moment(request: Moment.Request) = polyUseCase.moment(request).asLiveData()
    fun moment(id: String, request: Moment.Request) = polyUseCase.moment(id, request).asLiveData()
    fun moment(id: String) = polyUseCase.moment(id).asLiveData()
    fun moment() = polyUseCase.moment().asLiveData()
    fun postMultiple(
        userId: RequestBody,
        location: RequestBody,
        description: RequestBody,
        files: List<MultipartBody.Part>
    ) = polyUseCase.postMultiple(userId, location, description, files).asLiveData()
}