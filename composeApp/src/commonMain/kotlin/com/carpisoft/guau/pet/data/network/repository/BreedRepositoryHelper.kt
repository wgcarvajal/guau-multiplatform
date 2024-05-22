package com.carpisoft.guau.pet.data.network.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.data.network.constants.PetConstants
import com.carpisoft.guau.pet.data.network.model.BreedResponse
import com.carpisoft.guau.pet.domain.model.BreedResp

open class BreedRepositoryHelper {
    fun processResponse(response: Response<List<BreedResponse>>): Resp<List<BreedResp>> {
        val breedResponse = response.data
        var data: List<BreedResp>? = null
        if (breedResponse != null) {
            val breedsResp = mutableListOf<BreedResp>()
            for (breed in breedResponse) {
                breedsResp.add(
                    BreedResp(
                        id = breed.id,
                        name = breed.name,
                        image = "${NetworkConstants.SERVER}${PetConstants.BREED}/image/${breed.image}",
                        state = breed.state
                    )
                )
            }
            data = breedsResp
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, data)
    }
}