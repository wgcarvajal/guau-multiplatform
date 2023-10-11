package customer.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CustomerResponse(
    val id: Long,
    val identificationType: IdentificationTypeResponse,
    val identification: String,
    val name: String,
    val lastName: String,
    val email: String? = null,
    val address: String? = null,
    val phone: String? = null
)