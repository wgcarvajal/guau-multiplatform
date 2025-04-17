package com.carpisoft.guau.initialsetup.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CenterResponse(
    @SerialName("objectId")val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val image: String? = null
)
