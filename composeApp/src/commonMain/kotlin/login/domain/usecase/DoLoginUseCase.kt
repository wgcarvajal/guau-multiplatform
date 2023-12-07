package login.domain.usecase

import core.domain.model.Resp
import login.domain.model.LoginReq
import login.domain.model.LoginResp
import login.domain.port.LoginPort

class DoLoginUseCase(private val loginPort: LoginPort) {

    suspend operator fun invoke(loginReq: LoginReq): Resp<LoginResp> {
        return loginPort.doLogin(loginReq = loginReq)
    }
}