package login.domain.port

import core.domain.model.Resp
import login.domain.model.SignUpReq

interface SignUpPort {
    suspend fun doRegister(signUpReq: SignUpReq): Resp<Any>
}