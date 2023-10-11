package pet.domain.port

import core.domain.model.Resp
import pet.domain.model.PetReq

interface PetPort {
    suspend fun save(token:String,petReq: PetReq): Resp<Long>
}