package com.carpisoft.guau.customer.domain.model

data class CustomerResp(
    val id: String,
    val identificationType: IdentificationTypeResp,
    val identification: String,
    val name: String,
    val lastName: String,
    val email: String? = null,
    val address: String? = null,
    val phone: String? = null
)
