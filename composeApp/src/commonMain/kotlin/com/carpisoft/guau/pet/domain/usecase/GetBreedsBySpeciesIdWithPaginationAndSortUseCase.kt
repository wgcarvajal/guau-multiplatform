package com.carpisoft.guau.pet.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.BreedResp
import com.carpisoft.guau.pet.domain.port.BreedPort

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