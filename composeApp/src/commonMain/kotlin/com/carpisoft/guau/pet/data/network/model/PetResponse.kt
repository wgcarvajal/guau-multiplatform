package com.carpisoft.guau.pet.data.network.model

import com.carpisoft.guau.customer.data.network.model.CustomerCompleteResponse
import kotlinx.serialization.Serializable

@Serializable
data class PetResponse(
    val id: Long,
    val date: Long,
    val name: String,
    val description: String?,
    val breed: BreedCompleteResponse,
    val customer: CustomerCompleteResponse,
    val gender: GenderResponse
)