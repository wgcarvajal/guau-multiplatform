package com.carpisoft.guau.pet.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.SpeciesResp
import com.carpisoft.guau.pet.domain.port.SpeciesPort


class GetSpeciesUseCase(
    private val speciesPort: SpeciesPort
) {
    suspend operator fun invoke(
        token: String
    ): Resp<List<SpeciesResp>> {
        return speciesPort.getSpecies(token = token)
    }
}