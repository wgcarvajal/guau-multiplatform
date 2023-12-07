package pet.domain.port

import core.domain.model.Resp
import pet.domain.model.BreedResp

interface BreedPort {

    suspend fun getBreedsBySpeciesIdAndNameWithPaginationAndSort(
        token: String,
        speciesId: Int,
        name:String,
        page: Int,
        limit: Int
    ): Resp<List<BreedResp>>

    suspend fun getBreedsBySpeciesIdWithPaginationAndSort(
        token: String,
        speciesId: Int,
        page: Int,
        limit: Int
    ): Resp<List<BreedResp>>
}