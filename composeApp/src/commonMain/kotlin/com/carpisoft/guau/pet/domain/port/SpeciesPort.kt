package com.carpisoft.guau.pet.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.SpeciesResp


interface SpeciesPort {

    suspend fun getSpecies(token: String): Resp<List<SpeciesResp>>
}