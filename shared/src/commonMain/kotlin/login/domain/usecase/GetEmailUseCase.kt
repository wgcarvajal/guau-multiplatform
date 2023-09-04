package login.domain.usecase

import login.domain.port.LoginAuthorizationPort

class GetEmailUseCase(private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke(): String {
        return loginAuthorizationPort.getEmail()
    }
}