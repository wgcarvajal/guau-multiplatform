package com.carpisoft.guau.customer.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.domain.model.IdentificationTypeResp
import com.carpisoft.guau.customer.domain.port.IdentificationTypePort

class GetAllIdentificationTypeUseCase(private val identificationTypePort: IdentificationTypePort) {

    suspend operator fun invoke(token: String): Resp<List<IdentificationTypeResp>> {
        return identificationTypePort.getAllIdentificationType(token = token)
    }
}