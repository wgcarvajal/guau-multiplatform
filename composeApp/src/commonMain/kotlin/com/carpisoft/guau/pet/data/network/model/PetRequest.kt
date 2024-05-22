package com.carpisoft.guau.pet.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PetRequest (
    val date: Long,
    val name: String,
    val description: String? = null,
    val breed: Int,
    val customer: Long,
    val gender: Int
)