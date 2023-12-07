package pet.data.network.repository

import core.data.network.model.Response
import core.domain.model.Resp
import customer.domain.model.CustomerResp
import customer.domain.model.IdentificationTypeResp
import pet.data.network.model.PetResponse
import pet.domain.model.BreedCompleteResp
import pet.domain.model.GenderResp
import pet.domain.model.PetResp
import pet.domain.model.SpeciesResp

open class PetRepositoryHelper {

    fun processResponse(response: Response<Long>): Resp<Long> {
        val id = response.data
        return Resp(response.isValid, response.error, response.param, response.errorCode, id)
    }

    fun processPetsResponse(response: Response<List<PetResponse>>): Resp<List<PetResp>> {
        val petsResponse = response.data
        var data: List<PetResp>? = null
        if (petsResponse != null) {
            val petsResp = mutableListOf<PetResp>()
            for (pet in petsResponse) {
                petsResp.add(
                    PetResp(
                        id = pet.id,
                        date = pet.date,
                        name = pet.name,
                        description = pet.description,
                        breed = BreedCompleteResp(
                            id = pet.breed.id,
                            name = pet.breed.name,
                            image = pet.breed.image,
                            species = SpeciesResp(
                                id = pet.breed.species.id,
                                name = pet.breed.species.name,
                                image = pet.breed.species.image,
                                state = pet.breed.species.state
                            ), state = pet.breed.state
                        ),
                        customer = CustomerResp(
                            id = pet.customer.id,
                            identificationType = IdentificationTypeResp(
                                id = pet.customer.identificationType.id,
                                name = pet.customer.identificationType.name
                            ),
                            identification = pet.customer.identification,
                            name = pet.customer.name,
                            lastName = pet.customer.lastName,
                            email = pet.customer.email,
                            address = pet.customer.address,
                            phone = pet.customer.phone
                        ),
                        gender = GenderResp(
                            id = pet.gender.id,
                            name = pet.gender.name
                        )
                    )
                )
            }
            data = petsResp
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, data)
    }
}