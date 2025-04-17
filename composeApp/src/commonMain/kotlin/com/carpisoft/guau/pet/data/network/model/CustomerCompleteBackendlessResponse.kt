package com.carpisoft.guau.pet.data.network.model

import com.carpisoft.guau.initialsetup.data.network.model.CenterResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerCompleteBackendlessResponse(
    @SerialName("objectId") val id: String,
    val center: CenterResponse,
    val identificationType: String,
    val identification: String,
    val name: String,
    val lastName: String,
    val email: String? = null,
    val address: String? = null,
    val phone: String? = null
)