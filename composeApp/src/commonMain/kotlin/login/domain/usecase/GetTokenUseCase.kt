package login.domain.usecase

import login.domain.port.LoginAuthorizationPort

class GetTokenUseCase(private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke(): String {
        return loginAuthorizationPort.getToken()
    }
}