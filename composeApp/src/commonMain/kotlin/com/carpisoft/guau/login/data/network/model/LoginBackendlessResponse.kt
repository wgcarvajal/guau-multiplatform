package com.carpisoft.guau.login.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginBackendlessResponse(
    @SerialName("user-token") val userToken: String,
    val name:String,
    val email:String,
    val objectId:String
)