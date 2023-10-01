package customer.domain.usecase

import core.domain.model.Resp
import customer.domain.model.IdentificationTypeResp
import customer.domain.port.IdentificationTypePort

class GetAllIdentificationTypeUseCase(private val identificationTypePort: IdentificationTypePort) {

    suspend operator fun invoke(token: String): Resp<List<IdentificationTypeResp>> {
        return identificationTypePort.getAllIdentificationType(token = token)
    }
}