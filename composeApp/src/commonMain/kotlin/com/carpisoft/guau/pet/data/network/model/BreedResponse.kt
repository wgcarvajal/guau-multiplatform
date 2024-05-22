package com.carpisoft.guau.pet.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BreedResponse(
    val id: Int,
    val name: String,
    val image: String,
    val state: Int
)
