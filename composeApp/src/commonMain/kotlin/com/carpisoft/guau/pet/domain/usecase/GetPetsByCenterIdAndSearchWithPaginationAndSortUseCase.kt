package com.carpisoft.guau.pet.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.PetResp
import com.carpisoft.guau.pet.domain.port.PetPort

class GetPetsByCenterIdAndSearchWithPaginationAndSortUseCase(private val petPort: PetPort) {
    suspend operator fun invoke(
        token: String,
        centerId: String,
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