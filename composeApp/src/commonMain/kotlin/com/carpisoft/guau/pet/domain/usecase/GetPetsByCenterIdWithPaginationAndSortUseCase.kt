package com.carpisoft.guau.pet.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.PetResp
import com.carpisoft.guau.pet.domain.port.PetPort

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