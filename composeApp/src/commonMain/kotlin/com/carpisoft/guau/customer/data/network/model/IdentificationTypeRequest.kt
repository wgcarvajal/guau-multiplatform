package com.carpisoft.guau.customer.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class IdentificationTypeRequest(
    val id: Int,
    val name: String
)
