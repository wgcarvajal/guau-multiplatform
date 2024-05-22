package com.carpisoft.guau.login.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.domain.model.LoginReq
import com.carpisoft.guau.login.domain.model.LoginResp
import com.carpisoft.guau.login.domain.port.LoginPort

class DoLoginUseCase(private val loginPort: LoginPort) {

    suspend operator fun invoke(loginReq: LoginReq): Resp<LoginResp> {
        return loginPort.doLogin(loginReq = loginReq)
    }
}