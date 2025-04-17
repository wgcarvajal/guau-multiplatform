package com.carpisoft.guau.pet.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.PetReq
import com.carpisoft.guau.pet.domain.model.PetResp

interface PetPort {
    suspend fun save(token: String, petReq: PetReq): Resp<Long>

    suspend fun getPetsByCenterIdWithPaginationAndSort(
        token: String,
        centerId: String,
        page: Int,
        limit: Int
    ): Resp<List<PetResp>>

    suspend fun getPetsByCenterIdAndSearchWithPaginationAndSort(
        token: String,
        centerId: String,
        search: String,
        page: Int,
        limit: Int
    ): Resp<List<PetResp>>


}