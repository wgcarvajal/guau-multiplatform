package pet.domain.port

import core.domain.model.Resp
import pet.domain.model.BreedResp

interface BreedPort {

    suspend fun getBreedsBySpeciesIdWithPaginationAndSort(
        token: String,
        speciesId: Int,
        page: Int,
        limit: Int
    ): Resp<List<BreedResp>>
}