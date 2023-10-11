package pet.data.network.repository

import core.data.network.model.Response
import core.domain.model.Resp

open class PetRepositoryHelper {

    fun processResponse(response: Response<Long>): Resp<Long> {
        val id = response.data
        return Resp(response.isValid, response.error, response.param, response.errorCode, id)
    }
}