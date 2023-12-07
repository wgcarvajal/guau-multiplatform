package login.domain.port

import core.domain.model.Resp
import login.domain.model.LoginReq
import login.domain.model.LoginResp


interface LoginPort {
    suspend fun doLogin(loginReq: LoginReq): Resp<LoginResp>
}