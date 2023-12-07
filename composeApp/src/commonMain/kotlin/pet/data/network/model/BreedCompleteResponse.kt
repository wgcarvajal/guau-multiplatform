package pet.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BreedCompleteResponse(
    val id: Int,
    val name: String,
    val image: String,
    val species: SpeciesResponse,
    val state: Int
)