package com.poly.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.poly.core.data.source.remote.response.*
import com.poly.core.domain.usecase.PolyUseCase

class BusinessViewModel(private val polyUseCase: PolyUseCase) : ViewModel() {
    fun business(request: Business.Request) = polyUseCase.business(request).asLiveData()
    fun business(id: String, request: Business.Request) = polyUseCase.business(id, request).asLiveData()
    fun business(id: String) = polyUseCase.business(id).asLiveData()
    fun business() = polyUseCase.business().asLiveData()
}