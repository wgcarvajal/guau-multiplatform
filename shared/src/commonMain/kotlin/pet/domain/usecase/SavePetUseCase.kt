package pet.domain.usecase

import core.domain.model.Resp
import pet.domain.model.PetReq
import pet.domain.port.PetPort

class SavePetUseCase(private val petPort: PetPort) {
    suspend operator fun invoke(token: String, petReq: PetReq): Resp<Long> {
        return petPort.save(token = token, petReq = petReq)
    }
}