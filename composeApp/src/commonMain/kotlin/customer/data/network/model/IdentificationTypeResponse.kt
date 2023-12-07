package customer.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class IdentificationTypeResponse (
    val id: Int,
    val name: String
)