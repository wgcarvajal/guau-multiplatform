package com.carpisoft.guau.core.network.data.model.error

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val message:String,
    val param:String?=null,
    val errorCode:Int
)