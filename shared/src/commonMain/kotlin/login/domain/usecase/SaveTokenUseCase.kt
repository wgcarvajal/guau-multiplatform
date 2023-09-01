package login.domain.usecase

import login.domain.port.LoginAuthorizationPort


class SaveTokenUseCase(private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke(token: String) {
        loginAuthorizationPort.saveToken(token = token)
    }
}