package com.carpisoft.guau.core.network.data.model

data class Response<T>(
    val isValid:Boolean,
    val error:String?=null,
    val param: String?=null,
    val errorCode:Int?=null,
    val data:T?=null
)