package com.carpisoft.guau.login.domain.usecase

import com.carpisoft.guau.login.domain.port.LoginAuthorizationPort


class SaveEmailUseCase(private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke(email: String) {
        loginAuthorizationPort.saveEmail(email = email)
    }
}