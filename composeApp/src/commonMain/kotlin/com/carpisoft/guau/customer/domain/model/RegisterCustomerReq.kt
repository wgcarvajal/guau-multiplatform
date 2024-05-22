package com.carpisoft.guau.customer.domain.model

data class RegisterCustomerReq(
    val center:Int,
    val identificationType: Int,
    val identification: String,
    val name: String,
    val lastName: String,
    val email: String? = null,
    val address: String? = null,
    val phone: String? = null
)