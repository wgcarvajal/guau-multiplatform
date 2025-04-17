package com.carpisoft.guau.pet.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetBackendlessResponse(
    @SerialName("objectId") val id: String,
    @SerialName("birthday") val date: Long,
    val name: String,
    val description: String?,
    val breed: BreedCompleteBackendlessResponse,
    val customer: CustomerCompleteBackendlessResponse,
    val gender: String
)