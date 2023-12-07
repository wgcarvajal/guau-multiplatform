package login.domain.usecase

import login.domain.port.LoginAuthorizationPort


class SaveNameUseCase (private val loginAuthorizationPort: LoginAuthorizationPort) {
    suspend operator fun invoke(name:String)
    {
        loginAuthorizationPort.saveName(name = name)
    }
}