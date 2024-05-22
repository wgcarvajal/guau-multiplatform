package com.carpisoft.guau.login.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email:String,
    val password:String
)
