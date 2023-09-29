package pet.domain.usecase

import core.domain.model.Resp
import pet.domain.model.SpeciesResp
import pet.domain.port.SpeciesPort


class GetSpeciesUseCase(
    private val speciesPort: SpeciesPort
) {
    suspend operator fun invoke(
        token: String
    ): Resp<List<SpeciesResp>> {
        return speciesPort.getSpecies(token = token)
    }
}