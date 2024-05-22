package com.carpisoft.guau.initialsetup.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CenterResponse(
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val image: String? = null
)
