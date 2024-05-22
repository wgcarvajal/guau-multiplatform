package com.carpisoft.guau.customer.domain.port

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.domain.model.IdentificationTypeResp

interface IdentificationTypePort {

    suspend fun getAllIdentificationType(
        token: String
    ): Resp<List<IdentificationTypeResp>>
}