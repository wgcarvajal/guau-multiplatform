package login.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginRequest(
    val socialToken: String,
    val provider: String
)