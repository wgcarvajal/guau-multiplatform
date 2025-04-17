package com.carpisoft.guau.pet.data.network.repository

import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.domain.model.CustomerResp
import com.carpisoft.guau.customer.domain.model.IdentificationTypeResp
import com.carpisoft.guau.pet.data.network.model.PetBackendlessResponse
import com.carpisoft.guau.pet.data.network.model.PetResponse
import com.carpisoft.guau.pet.domain.model.BreedCompleteResp
import com.carpisoft.guau.pet.domain.model.GenderResp
import com.carpisoft.guau.pet.domain.model.PetResp
import com.carpisoft.guau.pet.domain.model.SpeciesResp

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
                        id = "${pet.id}",
                        date = pet.date,
                        name = pet.name,
                        description = pet.description,
                        breed = BreedCompleteResp(
                            id = "${pet.breed.id}",
                            name = pet.breed.name,
                            image = pet.breed.image,
                            species = SpeciesResp(
                                id = "${pet.breed.species.id}",
                                name = pet.breed.species.name,
                                image = pet.breed.species.image,
                                state = pet.breed.species.state
                            ), state = pet.breed.state
                        ),
                        customer = CustomerResp(
                            id = "${pet.customer.id}",
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

    fun processPetsBackendlessResponse(response: Response<List<PetBackendlessResponse>>): Resp<List<PetResp>> {
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
                                id = 1,
                                name = pet.customer.identificationType
                            ),
                            identification = pet.customer.identification,
                            name = pet.customer.name,
                            lastName = pet.customer.lastName,
                            email = pet.customer.email,
                            address = pet.customer.address,
                            phone = pet.customer.phone
                        ),
                        gender = GenderResp(
                            id = 1,
                            name = pet.gender
                        )
                    )
                )
            }
            data = petsResp
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, data)
    }
}