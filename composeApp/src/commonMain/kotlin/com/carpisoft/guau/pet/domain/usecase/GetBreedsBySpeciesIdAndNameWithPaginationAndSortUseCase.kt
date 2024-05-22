package com.carpisoft.guau.pet.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.BreedResp
import com.carpisoft.guau.pet.domain.port.BreedPort

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