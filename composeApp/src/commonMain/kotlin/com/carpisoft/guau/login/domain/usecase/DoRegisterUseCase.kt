package com.carpisoft.guau.login.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.domain.model.SignUpReq
import com.carpisoft.guau.login.domain.port.SignUpPort


class DoRegisterUseCase(private val signUpPort: SignUpPort) {

    suspend operator fun invoke(signUpReq: SignUpReq): Resp<Any> {
        return signUpPort.doRegister(signUpReq)
    }
}