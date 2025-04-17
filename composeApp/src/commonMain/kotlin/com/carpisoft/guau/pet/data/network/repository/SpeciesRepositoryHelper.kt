package com.carpisoft.guau.pet.data.network.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.data.network.constants.PetConstants
import com.carpisoft.guau.pet.data.network.model.SpeciesResponse
import com.carpisoft.guau.pet.domain.model.SpeciesResp


open class SpeciesRepositoryHelper {

    fun processResponse(response: Response<List<SpeciesResponse>>): Resp<List<SpeciesResp>> {
        val speciesResponse = response.data
        var data: List<SpeciesResp>? = null
        if (speciesResponse != null) {

            val speciesResp = mutableListOf<SpeciesResp>()
            for (species in speciesResponse) {
                speciesResp.add(
                    SpeciesResp(
                        id = species.id.toString(),
                        name = species.name,
                        image = "${NetworkConstants.SERVER}${PetConstants.SPECIES}/image/${species.image}",
                        state = species.state
                    )
                )
            }

            data = speciesResp
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, data)
    }
}