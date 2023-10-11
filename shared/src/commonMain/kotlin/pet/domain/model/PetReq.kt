package pet.domain.model

data class PetReq(
    val date: Long,
    val name: String,
    val description: String? = null,
    val breed: Int,
    val customer: Long,
    val gender: Int
)