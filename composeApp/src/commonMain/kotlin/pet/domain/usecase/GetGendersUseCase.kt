package pet.domain.usecase

import core.domain.model.Resp
import pet.domain.model.GenderResp
import pet.domain.port.GenderPort

class GetGendersUseCase(private val genderPort: GenderPort) {

    suspend operator fun invoke(token: String): Resp<List<GenderResp>> {
        return genderPort.getGenders(token = token)
    }
}