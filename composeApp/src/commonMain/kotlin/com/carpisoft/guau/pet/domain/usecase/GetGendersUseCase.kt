package com.carpisoft.guau.pet.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.GenderResp
import com.carpisoft.guau.pet.domain.port.GenderPort

class GetGendersUseCase(private val genderPort: GenderPort) {

    suspend operator fun invoke(token: String): Resp<List<GenderResp>> {
        return genderPort.getGenders(token = token)
    }
}