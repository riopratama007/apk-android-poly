package com.poly.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.poly.core.data.source.remote.response.*
import com.poly.core.domain.usecase.PolyUseCase

class ServiceViewModel(private val polyUseCase: PolyUseCase) : ViewModel() {
    fun serviceFileImage(request: Service.Request) = polyUseCase.serviceFileImage(request).asLiveData()
}