package com.carpisoft.guau.login.domain.usecase

import com.carpisoft.guau.login.domain.port.LoginAuthorizationPort

class GetEmailUseCase(private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke(): String {
        return loginAuthorizationPort.getEmail()
    }
}