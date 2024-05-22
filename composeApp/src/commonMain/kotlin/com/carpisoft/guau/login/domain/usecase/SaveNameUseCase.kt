package com.carpisoft.guau.login.domain.usecase

import com.carpisoft.guau.login.domain.port.LoginAuthorizationPort


class SaveNameUseCase (private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke(name:String)
    {
        loginAuthorizationPort.saveName(name = name)
    }
}