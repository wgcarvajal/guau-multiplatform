package customer.domain.port

import core.domain.model.Resp
import customer.domain.model.IdentificationTypeResp

interface IdentificationTypePort {

    suspend fun getAllIdentificationType(
        token: String,
    ): Resp<List<IdentificationTypeResp>>
}