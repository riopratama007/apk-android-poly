package com.poly.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.poly.core.data.source.remote.response.*
import com.poly.core.domain.usecase.PolyUseCase

class AuthViewModel(private val polyUseCase: PolyUseCase) : ViewModel() {
    fun register(requestRegister: Auth.RequestRegister) = polyUseCase.register(requestRegister).asLiveData()
    fun login(requestLogin: Auth.RequestLogin) = polyUseCase.login(requestLogin).asLiveData()
}