package login.domain.port

import core.domain.model.Resp
import login.domain.model.LoginResp
import login.domain.model.SocialLoginReq


interface SocialLoginPort {
    suspend fun doSocialLogin(socialLoginReq: SocialLoginReq): Resp<LoginResp>
}