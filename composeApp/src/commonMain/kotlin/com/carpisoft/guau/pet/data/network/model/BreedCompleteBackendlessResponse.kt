package com.carpisoft.guau.pet.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedCompleteBackendlessResponse(
    @SerialName("objectId") val id: String,
    val name: String,
    val image: String,
    val species: SpeciesBackendlessResponse,
    val state: Int
)
