package com.carpisoft.guau.customer.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterCustomerRequest(
    val center:Int,
    val identificationType: Int,
    val identification: String,
    val name: String,
    val lastName: String,
    val email: String? = null,
    val address: String? = null,
    val phone: String? = null
)