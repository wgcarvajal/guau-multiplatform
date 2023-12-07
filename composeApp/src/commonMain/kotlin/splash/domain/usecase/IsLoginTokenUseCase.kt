package splash.domain.usecase

import login.domain.port.LoginAuthorizationPort

class IsLoginTokenUseCase(private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke() = loginAuthorizationPort.getToken() != ""
}