package login.data.repository

import core.data.network.model.Response
import core.domain.model.Resp
import login.data.network.model.LoginResponse
import login.domain.model.LoginResp

open class LoginRepositoryHelper {

    fun processResponse(response: Response<LoginResponse>): Resp<LoginResp> {
        val loginResponse = response.data
        if (loginResponse != null) {
            return Resp(
                response.isValid, response.error, response.param, response.errorCode,
                LoginResp(
                    authorization = loginResponse.authorization,
                    email = loginResponse.email,
                    name = loginResponse.name
                )
            )
        }
        return Resp(response.isValid, response.error, response.param, response.errorCode, null)
    }
}