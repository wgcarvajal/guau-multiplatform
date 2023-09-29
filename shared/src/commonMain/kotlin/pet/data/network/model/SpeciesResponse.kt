package pet.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SpeciesResponse(
    val id: Int,
    val name: String,
    val image: String,
    val state: Int
)
