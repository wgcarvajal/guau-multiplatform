package pet.domain.usecase

import core.domain.model.Resp
import pet.domain.model.BreedResp
import pet.domain.port.BreedPort

class GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase(private val breedPort: BreedPort) {

    suspend operator fun invoke(
        token: String, speciesId: Int, name: String, page: Int, limit: Int
    ): Resp<List<BreedResp>> {
        return breedPort.getBreedsBySpeciesIdAndNameWithPaginationAndSort(
            token = token,
            speciesId = speciesId,
            name = name,
            page = page,
            limit = limit
        )
    }

}