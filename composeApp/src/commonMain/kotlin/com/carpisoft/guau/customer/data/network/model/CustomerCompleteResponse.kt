package com.carpisoft.guau.customer.data.network.model

import com.carpisoft.guau.initialsetup.data.network.model.CenterResponse
import kotlinx.serialization.Serializable

@Serializable
data class CustomerCompleteResponse(
    val id: Long,
    val center: CenterResponse,
    val identificationType: IdentificationTypeResponse,
    val identification: String,
    val name: String,
    val lastName: String,
    val email: String? = null,
    val address: String? = null,
    val phone: String? = null
)
