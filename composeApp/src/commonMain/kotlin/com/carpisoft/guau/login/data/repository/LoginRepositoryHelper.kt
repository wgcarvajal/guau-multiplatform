package com.carpisoft.guau.login.data.repository

import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.data.network.model.LoginBackendlessResponse
import com.carpisoft.guau.login.data.network.model.LoginResponse
import com.carpisoft.guau.login.domain.model.LoginResp

open class LoginRepositoryHelper {

    fun processResponse(response: Response<LoginResponse>): Resp<LoginResp> {
        val loginResponse = response.data
        if (loginResponse != null) {
            return Resp(
                response.isValid, response.error, response.param, response.errorCode,
                LoginResp(
                    authorization = loginResponse.authorization,
                    email = loginResponse.email,
                    name = loginResponse.name,
                    objectId = ""
                )
            )
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, null)
    }

    fun processBackendlessResponse(response: Response<LoginBackendlessResponse>): Resp<LoginResp> {
        val loginResponse = response.data
        if (loginResponse != null) {
            return Resp(
                response.isValid, response.error, response.param, response.errorCode,
                LoginResp(
                    authorization = loginResponse.userToken,
                    email = loginResponse.email,
                    name = loginResponse.name,
                    objectId = loginResponse.objectId
                )
            )
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, null)
    }
}