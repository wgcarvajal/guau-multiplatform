package pet.domain.port

import core.domain.model.Resp
import pet.domain.model.SpeciesResp


interface SpeciesPort {

    suspend fun getSpecies(token: String): Resp<List<SpeciesResp>>
}