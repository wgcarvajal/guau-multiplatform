package com.carpisoft.guau.pet.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.GenderResp

interface GenderPort {

    suspend fun getGenders(
        token: String
    ): Resp<List<GenderResp>>
}