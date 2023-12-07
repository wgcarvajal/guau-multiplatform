package pet.domain.port

import core.domain.model.Resp
import pet.domain.model.GenderResp

interface GenderPort {

    suspend fun getGenders(
        token: String
    ): Resp<List<GenderResp>>
}