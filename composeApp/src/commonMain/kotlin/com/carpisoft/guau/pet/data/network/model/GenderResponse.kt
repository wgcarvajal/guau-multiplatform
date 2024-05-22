package com.carpisoft.guau.pet.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GenderResponse(
    val id: Int,
    val name: String
)