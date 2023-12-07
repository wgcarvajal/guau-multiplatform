package pet.data.network.repository

import core.data.network.constants.NetworkConstants
import core.data.network.model.Response
import core.domain.model.Resp
import pet.data.network.constants.PetConstants
import pet.data.network.model.BreedResponse
import pet.domain.model.BreedResp

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
        return Resp(response.isValid, response.error, response.param,response.errorCode, data)
    }
}