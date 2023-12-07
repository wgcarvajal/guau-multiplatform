package pet.data.network.repository

import core.data.network.constants.NetworkConstants
import core.data.network.model.Response
import core.domain.model.Resp
import pet.data.network.constants.PetConstants
import pet.data.network.model.SpeciesResponse
import pet.domain.model.SpeciesResp


open class SpeciesRepositoryHelper {

    fun processResponse(response: Response<List<SpeciesResponse>>): Resp<List<SpeciesResp>> {
        val speciesResponse = response.data
        var data: List<SpeciesResp>? = null
        if (speciesResponse != null) {

            val speciesResp = mutableListOf<SpeciesResp>()
            for (species in speciesResponse) {
                speciesResp.add(
                    SpeciesResp(
                        id = species.id,
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