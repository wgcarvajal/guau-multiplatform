package pet.domain.usecase

import core.domain.model.Resp
import pet.domain.model.BreedResp
import pet.domain.port.BreedPort

class GetBreedsBySpeciesIdWithPaginationAndSortUseCase(private val breedPort: BreedPort) {
    suspend operator fun invoke(
        token: String, speciesId: Int, page: Int, limit: Int
    ): Resp<List<BreedResp>> {
        return breedPort.getBreedsBySpeciesIdWithPaginationAndSort(
            token = token,
            speciesId = speciesId,
            page = page,
            limit = limit
        )
    }
}