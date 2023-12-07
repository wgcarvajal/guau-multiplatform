package pet.domain.usecase

import core.domain.model.Resp
import pet.domain.model.PetResp
import pet.domain.port.PetPort

class GetPetsByCenterIdWithPaginationAndSortUseCase(private val petPort: PetPort) {

    suspend operator fun invoke(
        token: String,
        centerId: Int,
        page: Int,
        limit: Int
    ): Resp<List<PetResp>> {
        return petPort.getPetsByCenterIdWithPaginationAndSort(
            token = token,
            centerId = centerId,
            page = page,
            limit = limit
        )
    }
}