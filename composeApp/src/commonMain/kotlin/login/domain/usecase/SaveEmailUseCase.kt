package login.domain.usecase

import login.domain.port.LoginAuthorizationPort


class SaveEmailUseCase(private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke(email: String) {
        loginAuthorizationPort.saveEmail(email = email)
    }
}