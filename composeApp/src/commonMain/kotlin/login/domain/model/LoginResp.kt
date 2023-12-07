package login.domain.model

data class LoginResp (
    val  authorization:String,
    val email:String,
    val name:String
)