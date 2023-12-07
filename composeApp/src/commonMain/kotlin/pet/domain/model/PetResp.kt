package pet.domain.model

import customer.domain.model.CustomerResp

data class PetResp(
    val id: Long,
    val date: Long,
    val name: String,
    val description: String?,
    val breed: BreedCompleteResp,
    val customer: CustomerResp,
    val gender: GenderResp
)
