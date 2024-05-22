package com.carpisoft.guau.splash.domain.usecase

import com.carpisoft.guau.login.domain.port.LoginAuthorizationPort


class IsLoginTokenUseCase(private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke() = loginAuthorizationPort.getToken() != ""
}