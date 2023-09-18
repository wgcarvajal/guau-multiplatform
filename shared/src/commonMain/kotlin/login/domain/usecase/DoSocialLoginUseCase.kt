package login.domain.usecase

import core.domain.model.Resp
import login.domain.model.LoginResp
import login.domain.model.SocialLoginReq
import login.domain.port.SocialLoginPort


class DoSocialLoginUseCase (private val socialLoginPort: SocialLoginPort) {
    suspend operator fun invoke(socialLoginReq: SocialLoginReq): Resp<LoginResp> {
        return socialLoginPort.doSocialLogin(socialLoginReq = socialLoginReq)
    }
}