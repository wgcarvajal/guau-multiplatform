package com.carpisoft.guau.login.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.domain.model.SignUpReq

interface SignUpPort {
    suspend fun doRegister(signUpReq: SignUpReq): Resp<Any>
}