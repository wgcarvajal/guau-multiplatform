package com.carpisoft.guau.login.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.domain.model.LoginResp
import com.carpisoft.guau.login.domain.model.SocialLoginReq


interface SocialLoginPort {
    suspend fun doSocialLogin(socialLoginReq: SocialLoginReq): Resp<LoginResp>
}