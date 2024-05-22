package com.carpisoft.guau.pet.domain.usecase

import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.domain.model.PetReq
import com.carpisoft.guau.pet.domain.port.PetPort

class SavePetUseCase(private val petPort: PetPort) {
    suspend operator fun invoke(token: String, petReq: PetReq): Resp<Long> {
        return petPort.save(token = token, petReq = petReq)
    }
}