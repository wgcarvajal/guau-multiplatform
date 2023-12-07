package login.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val authorization:String,
    val email:String,
    val name:String
)