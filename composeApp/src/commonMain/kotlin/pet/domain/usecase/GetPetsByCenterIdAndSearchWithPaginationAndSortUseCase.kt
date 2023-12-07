package pet.domain.usecase

import core.domain.model.Resp
import pet.domain.model.PetResp
import pet.domain.port.PetPort

class GetPetsByCenterIdAndSearchWithPaginationAndSortUseCase(private val petPort: PetPort) {
    suspend operator fun invoke(
        token: String,
        centerId: Int,
        search: String,
        page: Int,
        limit: Int
    ): Resp<List<PetResp>> {
        return petPort.getPetsByCenterIdAndSearchWithPaginationAndSort(
            token = token,
            centerId = centerId,
            search = search,
            page = page,
            limit = limit
        )
    }
}