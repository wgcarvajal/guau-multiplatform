package com.carpisoft.guau.login.domain.model

data class SignUpReq(
    val email:String,
    val password:String,
    val name:String,
    val lastName:String
)
