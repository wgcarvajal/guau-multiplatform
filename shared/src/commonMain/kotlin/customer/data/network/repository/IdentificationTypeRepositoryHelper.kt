package customer.data.network.repository

import core.data.network.model.Response
import core.domain.model.Resp
import customer.data.network.model.IdentificationTypeResponse
import customer.domain.model.IdentificationTypeResp

open class IdentificationTypeRepositoryHelper {

    fun processResponse(response: Response<List<IdentificationTypeResponse>>): Resp<List<IdentificationTypeResp>> {
        val identificationTypesResponse = response.data
        var data: List<IdentificationTypeResp>? = null
        if (identificationTypesResponse != null) {
            val identificationTypesResp = mutableListOf<IdentificationTypeResp>()
            for (identificationTypes in identificationTypesResponse) {
                identificationTypesResp.add(
                    IdentificationTypeResp(
                        id = identificationTypes.id,
                        name = identificationTypes.name
                    )
                )
            }
            data = identificationTypesResp
        }
        return Resp(response.isValid, response.error, response.errorCode, data)
    }
}