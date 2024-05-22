package com.carpisoft.guau.login.domain.model

data class SocialLoginReq(
    val socialToken: String,
    val provider: String
)
