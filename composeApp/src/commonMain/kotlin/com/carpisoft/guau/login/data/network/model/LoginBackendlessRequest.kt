package com.carpisoft.guau.login.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginBackendlessRequest(
    val login: String,
    val password: String
)
