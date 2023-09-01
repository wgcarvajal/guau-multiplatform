package core.data.network.model.error

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val message:String,
    val errorCode:Int
)