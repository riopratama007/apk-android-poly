package com.poly.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.poly.core.data.source.remote.response.*
import com.poly.core.domain.usecase.PolyUseCase

class StoreViewModel(private val polyUseCase: PolyUseCase) : ViewModel() {
    fun store(userId: String) = polyUseCase.store(userId).asLiveData()
    fun store(request: Store.Request) = polyUseCase.store(request).asLiveData()
    fun store(id: String, request: Store.Request) = polyUseCase.store(id, request).asLiveData()
}