package com.carpisoft.guau.login.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.domain.model.LoginResp
import com.carpisoft.guau.login.domain.model.SocialLoginReq
import com.carpisoft.guau.login.domain.port.SocialLoginPort


class DoSocialLoginUseCase(private val socialLoginPort: SocialLoginPort) {
    suspend operator fun invoke(socialLoginReq: SocialLoginReq): Resp<LoginResp> {
        return socialLoginPort.doSocialLogin(socialLoginReq = socialLoginReq)
    }
}