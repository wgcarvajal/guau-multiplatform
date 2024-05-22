package com.carpisoft.guau.pet.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.BreedResp

interface BreedPort {

    suspend fun getBreedsBySpeciesIdAndNameWithPaginationAndSort(
        token: String,
        speciesId: Int,
        name: String,
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