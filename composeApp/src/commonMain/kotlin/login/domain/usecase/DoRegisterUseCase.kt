package login.domain.usecase

import core.domain.model.Resp
import login.domain.model.SignUpReq
import login.domain.port.SignUpPort


class DoRegisterUseCase (private val signUpPort: SignUpPort) {

    suspend operator fun invoke(signUpReq: SignUpReq): Resp<Any> {
        return signUpPort.doRegister(signUpReq)
    }
}