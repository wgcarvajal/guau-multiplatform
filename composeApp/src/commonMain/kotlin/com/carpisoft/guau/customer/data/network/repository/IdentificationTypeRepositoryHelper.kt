package com.carpisoft.guau.customer.data.network.repository

import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.data.network.model.IdentificationTypeResponse
import com.carpisoft.guau.customer.domain.model.IdentificationTypeResp

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
        return Resp(
            isValid = response.isValid,
            error = response.error,
            errorCode = response.errorCode,
            data = data
        )
    }
}