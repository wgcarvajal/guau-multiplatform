package com.carpisoft.guau.login.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest (
    val name:String,
    val lastName:String,
    val email:String,
    val password:String
)