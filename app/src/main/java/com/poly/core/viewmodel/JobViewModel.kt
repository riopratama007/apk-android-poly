package com.poly.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.poly.core.data.source.remote.response.*
import com.poly.core.domain.usecase.PolyUseCase

class JobViewModel(private val polyUseCase: PolyUseCase) : ViewModel() {
    fun job(request: Job.Request) = polyUseCase.job(request).asLiveData()
    fun job(id: String, request: Job.Request) = polyUseCase.job(id, request).asLiveData()
    fun job(id: String) = polyUseCase.job(id).asLiveData()
    fun job() = polyUseCase.job().asLiveData()
}