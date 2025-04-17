package com.carpisoft.guau.login.domain.usecase

import com.carpisoft.guau.login.domain.port.LoginAuthorizationPort

class SaveUserIdUseCase(
    private val loginAuthorizationPort: LoginAuthorizationPort
) {
    suspend operator fun invoke(userId: String) {
        loginAuthorizationPort.saveUserId(userId)
    }
}