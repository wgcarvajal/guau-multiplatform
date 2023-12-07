package pet.domain.port

import core.domain.model.Resp
import pet.domain.model.PetReq
import pet.domain.model.PetResp

interface PetPort {
    suspend fun save(token: String, petReq: PetReq): Resp<Long>

    suspend fun getPetsByCenterIdWithPaginationAndSort(
        token: String,
        centerId: Int,
        page: Int,
        limit: Int
    ): Resp<List<PetResp>>

    suspend fun getPetsByCenterIdAndSearchWithPaginationAndSort(
        token: String,
        centerId: Int,
        search: String,
        page: Int,
        limit: Int
    ): Resp<List<PetResp>>


}