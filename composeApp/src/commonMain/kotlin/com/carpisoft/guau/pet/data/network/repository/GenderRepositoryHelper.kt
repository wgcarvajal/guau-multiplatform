package com.carpisoft.guau.pet.data.network.repository

import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.data.network.model.GenderResponse
import com.carpisoft.guau.pet.domain.model.GenderResp

open class GenderRepositoryHelper {

    fun processResponse(response: Response<List<GenderResponse>>): Resp<List<GenderResp>> {
        val gendersResponse = response.data
        var data: List<GenderResp>? = null
        if (gendersResponse != null) {
            val gendersResp = mutableListOf<GenderResp>()
            for (genderResponse in gendersResponse) {
                gendersResp.add(
                    GenderResp(
                        id = genderResponse.id,
                        name = genderResponse.name
                    )
                )
            }
            data = gendersResp
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, data)
    }
}