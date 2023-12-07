package login.domain.model

data class SocialLoginReq(
    val socialToken: String,
    val provider: String
)
